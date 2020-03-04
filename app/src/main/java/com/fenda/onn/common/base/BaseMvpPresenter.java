package com.fenda.onn.common.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Date : 2020/3/4
 * Author : Davaid.lvfujiang
 * Desc :
 */
public class BaseMvpPresenter<V extends BaseView> implements BasePresenter<V> {
    protected Reference<V> mViewRef;

    @Override public void attachView(BaseView view) {
        mViewRef = new WeakReference(view);
    }

    @Override public void detachView() {
        mViewRef = null;
    }
}
