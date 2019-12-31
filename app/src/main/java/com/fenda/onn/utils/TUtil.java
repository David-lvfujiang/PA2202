package com.fenda.onn.utils;

import java.lang.reflect.ParameterizedType;

/**
 * @author kevin.wangzhiqiang
 * @time 2019/12/26 15:21
 * desc 泛型工具类
 */
public class TUtil {

    /**
     * 获取泛型对象
     *
     * @param o
     * @param index
     * @param <T>
     * @return
     */
    public static <T> T getT(Object o, int index) {
        try {
            return ((Class<T>) ((ParameterizedType) o.getClass().getGenericSuperclass()).getActualTypeArguments()[index]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }


}
