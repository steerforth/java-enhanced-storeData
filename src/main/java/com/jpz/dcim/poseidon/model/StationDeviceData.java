package com.jpz.dcim.poseidon.model;

import java.util.List;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description: 站点设备/测点数据封装
 * @Date: 2018-10-12 10:33
 */
public class StationDeviceData {
    /**
     * 采集站类型.
     */
    private String appKey;

    /**
     * 采集站id.
     */
    private String stationId;
    /**
     * 数据.
     */
    private List<DeviceData> data;

    public StationDeviceData(String appKey, String stationId, List<DeviceData> data){
        this.appKey = appKey;
        this.stationId = stationId;
        this.data = data;
    }

    public String getAppKey() {
        return appKey;
    }
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
    public String getStationId() {
        return stationId;
    }
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
    public List<DeviceData> getData() {
        return data;
    }
    public void setData(List<DeviceData> data) {
        this.data = data;
    }
}
