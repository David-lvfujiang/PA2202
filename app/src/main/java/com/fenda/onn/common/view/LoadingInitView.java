package com.fenda.onn.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fenda.onn.R;
import com.fenda.onn.utils.ImageUtil;


/**
*@author kevin.wangzhiqiang
*@time 2019/12/26 15:16
*desc  加载中视图
*/
public class LoadingInitView extends RelativeLayout {
    public LoadingInitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.common_view_init_loading, this);
        ImageView imgLoading = findViewById(R.id.img_init_loading);
        ImageUtil.loadGIFImage(R.mipmap.loading, imgLoading, R.mipmap.loading);
    }
}
