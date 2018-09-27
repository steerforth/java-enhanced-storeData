package com.jpz.dcim.poseidon;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description: kafka消费者
 * @Date: 2018-09-27 17:10
 */
@Component
public class KafkaConsumerLisener {
    private Logger logger = LoggerFactory.getLogger(KafkaConsumerLisener.class);

    @KafkaListener(topics = {"${kafka.topic.realtime}"})
    public void processMsg(List<ConsumerRecord<String, Object>> records){
        for(ConsumerRecord<String, Object> record: records){
            logger.info("线程id:{} 消费到消息：topic:{}--key:{}--value:{}",Thread.currentThread().getId(),record.topic(),record.key(),record.value());
        }
    }
}
