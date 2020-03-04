package com.fenda.onn.common.base;


/**
*@author kevin.wangzhiqiang
*@time 2019/12/26 15:21
*desc
*/
public interface BaseView {

    /*******内嵌加载*******/
    void showLoading(String title);
    void hideLoading();
    void showErrorTip(String msg);
    void showNetError();
    void hideNetError();
    void showContent();
    void hideContent();

}
