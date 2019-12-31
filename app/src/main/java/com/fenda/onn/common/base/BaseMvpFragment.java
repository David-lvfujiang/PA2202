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
*@time 2019/12/26 15:14
*desc  基类Mvp Fragment
*/
public abstract class BaseMvpFragment<T extends BasePresenter,E extends BaseModel> extends BaseFragment implements BaseView {

    protected T mPresenter;
    protected E mModel;
    public RxManager mRxManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mRxManager = new RxManager();
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this,1);
        if(mPresenter!=null){
            mPresenter.mContext = this.getActivity();
        }
        initPresenter();

        super.onCreate(savedInstanceState);

    }

    /**
     * 此处把Model和View与Presenter相关联
     */
    protected abstract void initPresenter();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.onDestroy();
        }
        if (mRxManager != null){
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
}
