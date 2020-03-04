package com.fenda.onn.http.base;

import com.fenda.onn.http.exception.RetrofitException;
import io.reactivex.Observer;

/**
 * Date : 2020/3/4
 * Author : Davaid.lvfujiang
 * Desc : 基类观察者
 */
public abstract class BaseObserver<V> implements Observer<V> {

    @Override public void onError(Throwable e) {
        RetrofitException.ResponeThrowable throwable = RetrofitException.retrofitException(e);
        onError(throwable);
    }

    public abstract void onError(RetrofitException.ResponeThrowable responeThrowable);
}
