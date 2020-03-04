package com.fenda.onn.common.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.fenda.onn.utils.TUtil;

/**
 * @author David-lvfujiang
 * @time 2019/12/26 15:13
 * desc  基类Mvp Activity
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity implements BaseView {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = TUtil.getT(this, 0);
        initPresenter();
        super.onCreate(savedInstanceState);
    }

    /**
     * 此处把Model和View与Presenter相关联
     */
    protected abstract void initPresenter();

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
