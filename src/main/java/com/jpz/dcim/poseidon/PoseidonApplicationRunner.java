package com.jpz.dcim.poseidon;

import com.jpz.dcim.poseidon.common.kafka.KafkaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description:
 * @Date: 2018-09-27 15:29
 */
@Component
@Order(1)
public class PoseidonApplicationRunner implements ApplicationRunner {
    Logger logger = LoggerFactory.getLogger(PoseidonApplicationRunner.class);

    @Autowired
    private KafkaProperties kafkaProps;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("服务加载完毕"+args);
        int concurrency = kafkaProps.getConcurrency();
        for(int i=0;i< concurrency;i++){
            DataMonitor monitor = new DataMonitor(kafkaProps,jedisPool.getResource());
            new Thread(monitor).start();
        }
    }
}
