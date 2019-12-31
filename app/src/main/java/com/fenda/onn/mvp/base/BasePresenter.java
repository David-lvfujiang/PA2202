package com.fenda.onn.mvp.base;

import android.content.Context;

import com.fenda.onn.common.rx.RxManager;


/**
*@author kevin.wangzhiqiang
*@time 2019/12/26 15:21
*desc
*/
public abstract class BasePresenter<V,M extends BaseModel> {
    public Context mContext;
    public M mModel;
    public V mView;
    public RxManager mRxManage = new RxManager();

    public void setVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }
    public void onStart(){
    };
    public void onDestroy() {
        mRxManage.clear();
    }




}
