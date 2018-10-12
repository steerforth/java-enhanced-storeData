package com.jpz.dcim.poseidon.listener;

import com.alibaba.fastjson.JSONObject;
import com.jpz.dcim.poseidon.common.redis.RedisClient;
import com.jpz.dcim.poseidon.model.DeviceData;
import com.jpz.dcim.poseidon.model.StationDeviceData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description: kafka消费者
 * @Date: 2018-09-27 17:10
 */
@Component
public class KafkaConsumerListener {
    private Logger logger = LoggerFactory.getLogger(KafkaConsumerListener.class);

    @Autowired
    private RedisClient redisClient;


    @KafkaListener(id="listener0",topicPartitions ={@TopicPartition(topic ="${kafka.topic.realtime}",partitions = {"0","5"})})
//    @KafkaListener(id="listener1",topics ="${kafka.topic.realtime}")
//    @KafkaListeners()
    public void processMsg(List<ConsumerRecord<String, String>> records){
        for(ConsumerRecord<String, String> record: records){
            logger.info("线程id:{} patition:{} 消费到消息：topic:{}--key:{}--value:{}",Thread.currentThread().getId(),record.partition(),record.topic(),record.key(),record.value());
        }
    }


    @KafkaListener(id="listener1",topicPartitions ={@TopicPartition(topic ="${kafka.topic.realtime}",partitions = {"1","6"})})
    public void processMsg1(List<ConsumerRecord<String, String>> records){
        for(ConsumerRecord<String, String> record: records){
            logger.info("线程id:{} patition:{} 消费到消息：topic:{}--key:{}--value:{}",Thread.currentThread().getId(),record.partition(),record.topic(),record.key(),record.value());
        }
    }


    @KafkaListener(id="listener2",topicPartitions ={@TopicPartition(topic ="${kafka.topic.realtime}",partitions = {"2","7"})})
    public void processMsg2(List<ConsumerRecord<String, String>> records){
        for(ConsumerRecord<String, String> record: records){
            logger.info("线程id:{} patition:{} 消费到消息：topic:{}--key:{}--value:{}",Thread.currentThread().getId(),record.partition(),record.topic(),record.key(),record.value());
        }
    }

    @KafkaListener(id="listener3",topicPartitions ={@TopicPartition(topic ="${kafka.topic.realtime}",partitions = {"3","8"})})
    public void processMsg3(List<ConsumerRecord<String, String>> records){
        for(ConsumerRecord<String, String> record: records){
            logger.info("线程id:{} patition:{} 消费到消息：topic:{}--key:{}--value:{}",Thread.currentThread().getId(),record.partition(),record.topic(),record.key(),record.value());
        }
    }

    @KafkaListener(id="listener4",topicPartitions ={@TopicPartition(topic ="${kafka.topic.realtime}",partitions = {"4","9"})})
    public void processMsg4(List<ConsumerRecord<String, String>> records){
        for(ConsumerRecord<String, String> record: records){
            logger.info("线程id:{} patition:{} 消费到消息：topic:{}--key:{}--value:{}",Thread.currentThread().getId(),record.partition(),record.topic(),record.key(),record.value());
        }
    }

    private void storeData(List<ConsumerRecord<String, String>> records){
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
        redisClient.hset(dd.getDeviceId()+".status","comm",dd.getData().get("status").toString());
    }

    private void storeCheckpointsData(DeviceData dd) {
        Map<String, String> map = this.convertToStringValue(dd.getData());
        map.put("lastUpdatedAt", System.currentTimeMillis()+"");
        redisClient.hmset(dd.getDeviceId()+".checkpoints",map);
    }

    private Map<String, String> convertToStringValue(Map<String, Object> data) {
        Map<String,String> map =new HashMap<>();
        for(Map.Entry<String,Object> entry: data.entrySet()){
            map.put(entry.getKey()+".value", entry.getValue().toString());
        }
        return map;
    }
//    @KafkaListener(topicPattern = "topic.*")
//    @KafkaListener(topics ="${kafka.topic.realtime}",containerFactory = "kafkaListenerContainerFactory")
//    public void listen(@Payload String data, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,@Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp) {
//        logger.info("线程id:{} 消费到消息：topic:{}--partition:{}--value:{}",Thread.currentThread().getId(),topic,partition,data);
//    }

}
