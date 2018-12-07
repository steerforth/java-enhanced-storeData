package com.jpz.dcim.poseidon;

import com.alibaba.fastjson.JSONObject;
import com.jpz.dcim.poseidon.common.kafka.KafkaConsumerFactory;
import com.jpz.dcim.poseidon.common.kafka.KafkaFactory;
import com.jpz.dcim.poseidon.common.kafka.KafkaProperties;
import com.jpz.dcim.poseidon.model.DeviceData;
import com.jpz.dcim.poseidon.model.StationDeviceData;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description: 数据监视器
 * @Date: 2018-09-27 15:35
 */
public class DataMonitor implements Runnable{
    Logger logger = LoggerFactory.getLogger(DataMonitor.class);
    private KafkaProperties kafkaProps;
    private Jedis jedis;
    private boolean terminate = false;
    private Consumer consumer;

    public DataMonitor(KafkaProperties kafkaProps, Jedis jedis) {
        this.kafkaProps = kafkaProps;
        this.jedis = jedis;
    }

    @Override
    public void run() {
        try {
            KafkaFactory factory = new KafkaConsumerFactory(this.kafkaProps);
            this.consumer = (Consumer) factory.create();
            consumer.subscribe(kafkaProps.getTopics());
            while(!terminate){
                ConsumerRecords<String, String> records = consumer.poll(this.kafkaProps.getPollTime());
                this.storeData(records);
                Thread.yield();
            }
        }finally {
            if (this.jedis != null){
                this.jedis.close();
            }
            this.consumer.close();
        }
    }

    private void storeData(ConsumerRecords<String, String> records){
        for(ConsumerRecord<String, String> record: records){
            logger.info("线程id:{} 消费patition:{}的消息：【topic】:{} 【key】:{}【value】:{}",Thread.currentThread().getId(),record.partition(),record.topic(),record.key(),record.value());
            List<DeviceData> data = JSONObject.parseObject(record.value(), StationDeviceData.class).getData();
            if(data!=null){
                for(DeviceData dd: data){
                    if (null != dd.getData() && dd.getData().size() > 0)
                        this.storeCheckpointsOrCommEventData(dd);
                }
            }
        }
    }

    private void storeCheckpointsOrCommEventData(DeviceData dd) {
        if (dd.getData().containsKey("commEvent")){
            this.storeCommEventData(dd);
        }else{
            this.storeCheckpointsData(dd);
        }
    }

    private void storeCommEventData(DeviceData dd) {
        jedis.hset(dd.getDeviceId()+".status","comm",dd.getData().get("status").toString());
    }

    private void storeCheckpointsData(DeviceData dd) {
        Map<String, String> map = this.convertToStringValue(dd.getData());
        map.put("lastUpdatedAt", System.currentTimeMillis()+"");
        jedis.hmset(dd.getDeviceId()+".checkpoints",map);
    }

    private Map<String, String> convertToStringValue(Map<String, Object> data) {
        Map<String,String> map =new HashMap<>();
        for(Map.Entry<String,Object> entry: data.entrySet()){
            map.put(entry.getKey()+".value", entry.getValue().toString());
        }
        return map;
    }
}
