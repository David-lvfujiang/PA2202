package com.fenda.onn.common.view;

import android.content.Context;

import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

import com.fenda.onn.R;

/**
*@author kevin.wangzhiqiang
*@time 2019/12/26 15:17
*desc 空视图
*/
public class NoDataView extends RelativeLayout {

    private final RelativeLayout mRlNoDataRoot;
    private final ImageView mImgNoDataView;

    public NoDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.common_view_no_data,this);
        mRlNoDataRoot = findViewById(R.id.rl_no_data_root);
        mImgNoDataView = findViewById(R.id.img_no_data);
    }

    public void setNoDataBackground(@ColorRes int  colorResId){
        mRlNoDataRoot.setBackgroundColor(getResources().getColor(colorResId));
    }

    public void setNoDataView(@DrawableRes int imgResId){
        mImgNoDataView.setImageResource(imgResId);
    }
}