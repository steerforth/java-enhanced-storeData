package com.jpz.dcim.poseidon.common.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description: kafka生产者配置
 * @Date: 2018-09-27 16:50
 */
@Configuration
@EnableKafka
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-server}")
    private String server;

    @Value("${spring.kafka.producer.retries}")
    private int retries;
    /**
     * 满足达到发送大小batchSize或时间间隔linger其中之一就发送
     */
    //单位 Bytes
    @Value("${spring.kafka.producer.batch-size}")
    private int batchSize;
    //单位 ms
    private int linger = 100;

    @Value("${spring.kafka.producer.buffer-memory}")
    private int bufferMemory;
    /**
     * 0表示发送的消息不会等待broker的成功响应，会得到最大的系统吞吐量
     * 1表示会在leader partition收到消息时得到broker的一个确认
     * -1表示会在所有备份的partition收到消息时得到broker的确认，最高的可靠性保证
     */
    @Value("${spring.kafka.producer.acks}")
    private String acks;
    private int blockConfig = 1000;


    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<String, String>(producerFactory());
    }

    private ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    private Map<String,Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG,acks);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString().replace("-",""));
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG,blockConfig);
        return props;
    }

}
