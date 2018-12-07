package com.jpz.dcim.poseidon.common.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.util.Properties;
import java.util.UUID;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description: 消费者工厂类
 * @Date: 2018-12-07 09:52
 */
public class KafkaConsumerFactory extends  KafkaFactory{
    private KafkaProperties kafkaProps;

    public KafkaConsumerFactory(KafkaProperties kafkaProps){
        this.kafkaProps = kafkaProps;
    }

    private KafkaConsumerFactory() {
    }

    @Override
    protected Closeable create(Properties props) {
        return new KafkaConsumer<>(props);
    }

    @Override
    protected Properties prepareProperties() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProps.getBootstrapServer());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProps.getGroup());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProps.getConsumer().isEnableAutoCommit());
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, kafkaProps.getConsumer().getAutoCommitInterval());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaProps.getTimeout());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProps.getConsumer().getAutoOffsetReset());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString().replace("-", ""));
        return props;
    }
}
