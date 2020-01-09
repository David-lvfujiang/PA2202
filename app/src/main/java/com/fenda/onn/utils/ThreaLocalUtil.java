package com.fenda.onn.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.fenda.onn.AppApplication;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/6
 * @Describe: 单例模式获取ThreadLocal对象
 */
public class ThreaLocalUtil {
    private ThreaLocalUtil() {
    }

    public static ThreadLocal getSharedPreferences() {
        return Holder.mThreaLocal;
    }

    //在静态内部类中持有ThreadLocal的实例
    private static class Holder {
        private static ThreadLocal<Integer> mThreaLocal = new ThreadLocal<Integer>();
    }

}
