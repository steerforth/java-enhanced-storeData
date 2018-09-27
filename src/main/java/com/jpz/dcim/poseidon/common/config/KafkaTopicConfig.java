package com.jpz.dcim.poseidon.common.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description:
 * @Date: 2018-09-27 19:52
 */
@Configuration
public class KafkaTopicConfig implements InitializingBean {
    @Value("${kafka.topic.realtime}")
    private String topic_realtime;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.setProperty("kafka.topic.realtime", topic_realtime);
    }
}
