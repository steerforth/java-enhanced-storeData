package com.jpz.dcim.poseidon.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description:
 * @Date: 2018-09-27 17:44
 */
@RestController
public class KafkaSendController {
    private final static Logger logger = LoggerFactory.getLogger(KafkaSendController.class);

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @GetMapping(value = "/{topic}/send")
    public void sendMeessageTotopic1(@PathVariable String topic, @RequestParam(value = "partition",defaultValue = "0") int partition) {
        logger.info("【start send message to {}】",topic);
        kafkaTemplate.send(topic,partition,JSONObject.toJSONString("a"), JSONObject.toJSONString("b"));
    }

}
