package com.fenda.onn;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kevin.wangzhiqiang
 * @time 2019/12/26 15:21
 * desc  应用入口
 */
public class AppApplication extends Application {
    private static Context mContext;
    public static List<Activity> mActivitys = new ArrayList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
    public static Context getContext() {
        return mContext;
    }


    /**
     * 向List中添加一个活动
     *
     * @param activity 活动
     */

    public static void addActivity(Activity activity) {


        mActivitys.add(activity);

    }


    /**
     * 从List中移除活动
     *
     * @param activity 活动
     */

    public static void removeActivity(Activity activity) {


        mActivitys.remove(activity);

    }


    /**
     * 将List中存储的活动全部销毁掉
     */

    public static void finishAll() {


        for (Activity activity : mActivitys) {


            if (!activity.isFinishing()) {


                activity.finish();

            }

        }

    }
}
