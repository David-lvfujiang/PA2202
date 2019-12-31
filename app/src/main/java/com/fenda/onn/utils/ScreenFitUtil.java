package com.fenda.onn.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

/**
*@author kevin.wangzhiqiang
*@time 2019/12/30 9:19
*desc  屏幕适配工具类
*/
public class ScreenFitUtil {
    private static float sRoncompatDennsity;
    private static float sRoncompatScaledDensity;

    public static void  setCustomDensity(@NonNull Activity activity, final @NonNull Application application) {

        //application
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sRoncompatDennsity == 0) {
            sRoncompatDennsity = appDisplayMetrics.density;
            sRoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sRoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }
                @Override
                public void onLowMemory() {

                }
            });
        }
        //计算宽为360dp 同理可以设置高为640dp的根据实际情况
        final float targetDensity = appDisplayMetrics.widthPixels / 360;
        final float targetScaledDensity = targetDensity * (sRoncompatScaledDensity / sRoncompatDennsity);
        final int targetDensityDpi = (int) (targetDensity * 160);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;
        appDisplayMetrics.scaledDensity = targetScaledDensity;

        //activity
        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();

        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
    }

}
