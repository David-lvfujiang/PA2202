package com.fenda.onn.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.ColorRes;

import com.fenda.onn.R;


/**
*@author kevin.wangzhiqiang
*@time 2019/12/26 15:17
*desc  服务器异常视图
*/
public class NetErrorView extends RelativeLayout {
    private OnClickListener mOnClickListener;
    private final RelativeLayout mRlNetWorkError;

    public NetErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.common_view_net_error,this);
        findViewById(R.id.btn_net_refresh).setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                if(mOnClickListener != null){
                    mOnClickListener.onClick(v);
                }
            }
        });
        mRlNetWorkError = findViewById(R.id.rl_net_error_root);
    }
    public void setRefreshBtnClickListener(OnClickListener listener){
        mOnClickListener = listener;
    }

    public void setNetErrorBackground(@ColorRes int  colorResId){
        mRlNetWorkError.setBackgroundColor(getResources().getColor(colorResId));
    }
}
