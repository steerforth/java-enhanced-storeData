package com.jpz.dcim.poseidon.common.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.util.Properties;
import java.util.UUID;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description: 生产者工厂类
 * @Date: 2018-12-07 11:05
 */
public class KafkaProducerFactory extends KafkaFactory {
    private KafkaProperties kafkaProps;

    public KafkaProducerFactory(KafkaProperties kafkaProps){
        this.kafkaProps = kafkaProps;
    }

    private KafkaProducerFactory() {
    }

    @Override
    protected Closeable create(Properties props) {
        return new KafkaProducer<>(props);
    }

    @Override
    protected Properties prepareProperties() {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaProps.getBootstrapServer());
        props.setProperty(ProducerConfig.ACKS_CONFIG, this.kafkaProps.getProducer().getAcks());
        props.put(ProducerConfig.RETRIES_CONFIG,this.kafkaProps.getProducer().getRetries());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, this.kafkaProps.getProducer().getBatchSize());
        props.put(ProducerConfig.LINGER_MS_CONFIG, this.kafkaProps.getProducer().getLingerMs());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, this.kafkaProps.getProducer().getBufferMemory());
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, this.kafkaProps.getProducer().getMaxBlockMs());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);//"org.apache.kafka.common.serialization.StringSerializer"
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class );
        props.put(ProducerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString().replace("-", ""));
        return props;
    }
}
