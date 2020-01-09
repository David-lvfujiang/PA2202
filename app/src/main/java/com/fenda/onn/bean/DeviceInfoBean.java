package com.fenda.onn.bean;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/7
 * @Describe:设备信息实体
 */
public class DeviceInfoBean {
    private String firmwareVersion;
    private String bluetoothAddress;
    private String appVersion;
    private String language;
    private String privacyPolicy;

    public DeviceInfoBean(String firmwareVersion, String bluetoothAddress,
                          String appVersion, String language, String privacyPolicy) {
        this.firmwareVersion = firmwareVersion;
        this.bluetoothAddress = bluetoothAddress;
        this.appVersion = appVersion;
        this.language = language;
        this.privacyPolicy = privacyPolicy;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public void setBluetoothAddress(String bluetoothAddress) {
        this.bluetoothAddress = bluetoothAddress;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPrivacyPolicy(String privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public String getBluetoothAddress() {
        return bluetoothAddress;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getLanguage() {
        return language;
    }

    public String getPrivacyPolicy() {
        return privacyPolicy;
    }

    @Override
    public String toString() {
        return "DeviceInfoBean{" +
                "firmwareVersion='" + firmwareVersion + '\'' +
                ", bluetoothAddress='" + bluetoothAddress + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", language='" + language + '\'' +
                ", privacyPolicy='" + privacyPolicy + '\'' +
                '}';
    }
}
