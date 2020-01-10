package com.fenda.onn.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/6
 * @Describe: FM台实体
 */
public class FmStationBean extends LitePalSupport {
    private String stationName;

    public FmStationBean() {
    }

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
