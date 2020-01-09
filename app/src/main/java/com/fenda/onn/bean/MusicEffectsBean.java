package com.fenda.onn.bean;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/2
 * @Describe:音效实体类
 */
public class MusicEffectsBean {
    private int effectsName;
    private int imgResId;

    public MusicEffectsBean(int effectsName, int imgResId) {
        this.effectsName = effectsName;
        this.imgResId = imgResId;
    }

    public void setEffectsName(int effectsName) {
        this.effectsName = effectsName;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public int getEffectsName() {
        return effectsName;
    }

    public int getImgResId() {
        return imgResId;
    }
}
