package com.fenda.onn.common.rx;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.Observable;

/**
 * @author kevin.wangzhiqiang
 * @time 2019/12/27 13:58
 * desc  用RxRelay实现的EventBus
 */
public class RxBus {

    private volatile static RxBus mDefaultInstance;
    private final Relay<Object> mBus;

    private RxBus() {
        mBus = PublishRelay.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (mDefaultInstance == null) {
            synchronized (RxBus.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new RxBus();
                }
            }
        }
        return mDefaultInstance;
    }

    /**
     * 发送事件
     */
    public void post(Object event) {
        mBus.accept(event);
    }

    /**
     * 使用Rxlifecycle解决RxJava引起的内存泄漏
     */
    public <T> Observable<T> toObservable(LifecycleOwner owner, final Class<T> eventType) {
        LifecycleProvider<Lifecycle.Event> provider = AndroidLifecycle.createLifecycleProvider(owner);
        return mBus.ofType(eventType).compose(provider.<T>bindToLifecycle());
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    public void reset() {
        mDefaultInstance = null;
    }
}
