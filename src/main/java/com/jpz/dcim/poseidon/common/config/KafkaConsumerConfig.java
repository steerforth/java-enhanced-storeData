package com.jpz.dcim.poseidon.common.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description: kafka消费者配置
 * @Date: 2018-09-27 15:48
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Value("${spring.kafka.listener.concurrency}")
    private int concurrency;
    @Value("${spring.kafka.bootstrap-server}")
    private String server;

    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private boolean enableAutoCommit;
    @Value("${spring.kafka.consumer.auto-commit-interval}")
    private int commitInterval;
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String resetStrategy;

    private int sessionTimeout = 30000;
    private int pollTimeout = 1500;



    @Bean    
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>(); 
        factory.setConsumerFactory(consumerFactory());
        //和kafka patitions数量设置一样
        factory.setConcurrency(concurrency);
        factory.setBatchListener(true);
        factory.getContainerProperties().setPollTimeout(pollTimeout);
        return factory;   
    }

//    @Bean//消息监听器
//    public KafkaConsumerLisener kafkaConsumerLisener() {
//        return new KafkaConsumerLisener();
//    }

    private ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, commitInterval);
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString().replace("-",""));
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, resetStrategy);
//        propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
        return propsMap;
    }

}
