package com.fenda.onn.common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.fenda.onn.common.rx.RxManager;
import com.fenda.onn.mvp.base.BaseModel;
import com.fenda.onn.mvp.base.BasePresenter;
import com.fenda.onn.mvp.base.BaseView;
import com.fenda.onn.utils.TUtil;


/**
*@author kevin.wangzhiqiang
*@time 2019/12/26 15:13
*desc  基类Mvp Activity
*/
public abstract class BaseMvpActivity<T extends BasePresenter, M extends BaseModel> extends BaseActivity implements BaseView {

    protected T mPresenter;
    protected M mModel;

    public RxManager mRxManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mRxManager = new RxManager();
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        initPresenter();
        super.onCreate(savedInstanceState);
    }

    /**
     * 此处把Model和View与Presenter相关联
     */
    protected abstract void initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        if (mRxManager != null) {
            mRxManager.clear();
        }


    }

    @Override
    public void showLoading(String title) {
        showInitLoadView();

    }

    @Override
    public void hideLoading() {
        hideInitLoadView();

    }


    @Override
    public void showNetError() {
        showNetWorkErrView();

    }

    @Override
    public void hideNetError() {
        hideNetWorkErrView();

    }

    @Override
    public void showContent() {
        showContentView(true);

    }

    @Override
    public void hideContent() {
        showContentView(false);
    }
}
