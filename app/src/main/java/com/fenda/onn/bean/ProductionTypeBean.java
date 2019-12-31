package com.fenda.onn.bean;

/**
 * @author kevin.wangzhiqiang
 * @time 2019/12/30 10:21
 * desc  产品类型实体
 */
public class ProductionTypeBean {
    private int imgResId;
    private String name;

    public ProductionTypeBean() {
    }

    public ProductionTypeBean(int imgResId, String name) {
        this.imgResId = imgResId;
        this.name = name;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
