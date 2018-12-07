package com.jpz.dcim.poseidon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

//    @Autowired
//    private KafkaTemplate<String,String> kafkaTemplate;

    @GetMapping(value = "/{topic}/send")
    public void sendMeessageTotopic1(@PathVariable String topic, @RequestParam(value = "partition",defaultValue = "0") int partition) {
        logger.info("【start send message to {}】",topic);

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
