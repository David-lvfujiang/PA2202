package com.fenda.onn.bean;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/6
 * @Describe: FM台实体
 */
public class FmStationBean {
    private String stationName;

    public FmStationBean(String stationName) {
        this.stationName = stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }
}
