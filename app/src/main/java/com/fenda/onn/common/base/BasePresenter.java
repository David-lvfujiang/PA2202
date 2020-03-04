package com.fenda.onn.common.base;

/**
*@author kevin.wangzhiqiang
*@time 2019/12/26 15:21
*desc
*/
public interface  BasePresenter<V extends BaseView> {
    void attachView(V view);
    void detachView();
}
