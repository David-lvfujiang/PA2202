package com.fenda.onn.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/13
 * @Describe: 创建弹窗工具类
 */
public class PopupWindowUtil {
    private static PopupWindow mPopupWindow;
    private static final int HIGH = -2;
    private static final int WIDTH = -1;
    private static final float HIGH_PERCENTAGE = 0.8f;
    private static final float WIDTH_PERCENTAGE = 0.9f;

    /**
     * @param context               PopupWindow显示的载体
     * @param view                  PopupWindow显示的布局
     * @param isFocus               是否获取焦点
     * @param isProportionateHeight 是否按比例分配宽高
     * @param position              显示的位置
     */
    public static void createPopupWindow(Activity context, View view, boolean isFocus, boolean isProportionateWidth,
                                         boolean isProportionateHeight, int position) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        //获取窗口宽度和高度
        DisplayMetrics outMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        if (isProportionateHeight) {
            if (isProportionateWidth) {
                mPopupWindow = new PopupWindow(view, (int) (widthPixels * WIDTH_PERCENTAGE),
                        (int) (heightPixels * HIGH_PERCENTAGE), true);
            } else {
                mPopupWindow = new PopupWindow(view, WIDTH, (int) (heightPixels * HIGH_PERCENTAGE), true);
            }
        } else {
            if (isProportionateWidth) {
                mPopupWindow = new PopupWindow(view, (int) (widthPixels * WIDTH_PERCENTAGE), HIGH, true);
            } else {
                mPopupWindow = new PopupWindow(view, WIDTH, HIGH, true);
            }
        }
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(isFocus);
        mPopupWindow.setFocusable(isFocus);
        //设置背景为半透明
        lp.alpha = 0.5f;
        context.getWindow().setAttributes(lp);
        //监听PopupWindow关闭时将透明度设置成原来
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 1f;
                context.getWindow().setAttributes(lp);
            }
        });
        //设置弹窗位置PopupWindow的相关参数
        mPopupWindow.showAtLocation(context.getWindow().getDecorView(), position, 0, 0);
    }

    /**
     * 关闭弹窗
     */
    public static void closePopPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }
}
