package com.fenda.onn.bean;

/**
 * @author kevin.wangzhiqiang
 * @time 2019/12/30 17:31
 * desc  设备连接实体
 */
public class DeviceConnectionBean {
    private int deviceResId;
    private String name;
    private String connectStatus;

    public DeviceConnectionBean() {
    }

    public DeviceConnectionBean(int deviceResId, String name, String connectStatus) {
        this.deviceResId = deviceResId;
        this.name = name;
        this.connectStatus = connectStatus;
    }

    public int getDeviceResId() {
        return deviceResId;
    }

    public void setDeviceResId(int deviceResId) {
        this.deviceResId = deviceResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(String connectStatus) {
        this.connectStatus = connectStatus;
    }
}
