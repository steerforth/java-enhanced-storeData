package com.jpz.dcim.poseidon;

import com.alibaba.fastjson.JSONObject;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description:
 * @Date: 2018-10-11 19:05
 */
public class KafkaSendThreadTest implements Runnable{
    private KafkaTemplate<String,String> kafkaTemplate;

    private String topic;

    private int partition;

    public KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }

    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    @Override
    public void run() {
        Map<String,String> map = new HashMap<>();
        map.put("a","456");
        map.put("b","123");
        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic,partition, JSONObject.toJSONString("a"), JSONObject.toJSONString(map));
        SuccessCallback<SendResult<String, String>> successCallback = new SuccessCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                //成功业务逻辑
                System.out.println("发送kafka成功");
            }
        };

        FailureCallback failureCallback = new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                //失败业务逻辑
                throw new RuntimeException(ex);
            }
        };
        listenableFuture.addCallback(successCallback, failureCallback);
    }
}
