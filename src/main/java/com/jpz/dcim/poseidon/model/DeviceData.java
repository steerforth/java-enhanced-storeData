package com.jpz.dcim.poseidon.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description: 设备数据
 * @Date: 2018-10-12 10:34
 */
public class DeviceData {
    /**
     * 设备id.
     */
    @JSONField(name="equipId")
    private String deviceId;

    /**
     * 数据.
     */
    private Map<String, Object> data;

    /**
     * 时间戳.
     */
    private long timestamp;

    public DeviceData(String deviceId, Map<String, Object> data){
        this.deviceId = deviceId;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
