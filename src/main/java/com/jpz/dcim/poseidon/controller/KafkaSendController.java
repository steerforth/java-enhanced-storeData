package com.jpz.dcim.poseidon.controller;

import com.alibaba.fastjson.JSONObject;
import com.jpz.dcim.poseidon.KafkaSendThreadTest;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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

        for(int i =0; i<1;i++){
            KafkaSendThreadTest thread = new KafkaSendThreadTest();
            thread.setKafkaTemplate(kafkaTemplate);
            thread.setTopic(topic);
            thread.setPartition(i%3);
            Thread t = new Thread(thread);
            t.start();
        }

//        Map<String,String> map = new HashMap<>();
//        map.put("a","456");
//        map.put("b","123");
//        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic,partition,JSONObject.toJSONString("a"), JSONObject.toJSONString(map));
//        SuccessCallback<SendResult<String, String>> successCallback = new SuccessCallback<SendResult<String, String>>() {
//            @Override
//            public void onSuccess(SendResult<String, String> result) {
//                //成功业务逻辑
//                System.out.println("发送kafka成功");
//            }
//        };
//
//        FailureCallback failureCallback = new FailureCallback() {
//            @Override
//            public void onFailure(Throwable ex) {
//                //失败业务逻辑
//                throw new RuntimeException(ex);
//            }
//        };
//        listenableFuture.addCallback(successCallback, failureCallback);

    }

}
