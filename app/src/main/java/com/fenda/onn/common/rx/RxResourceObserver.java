package com.fenda.onn.common.rx;




import com.fenda.onn.AppApplication;
import com.fenda.onn.R;
import com.fenda.onn.common.exception.ServerException;
import com.fenda.onn.http.base.BaseResponse;
import com.fenda.onn.mvp.base.BaseView;
import com.fenda.onn.utils.NetUtil;
import com.fenda.onn.utils.ToastUtils;

import io.reactivex.observers.ResourceObserver;


/********************使用例子********************/
/*_apiService.login(mobile, verifyCode)
        .//省略
        .subscribe(new RxResourceObserver<User user>(mContext,false) {
@Override
public void _onNext(User user) {
        // 处理user
        }

@Override
public void _onError(String msg) {
        ToastUtil.showShort(mActivity, msg);
        });*/

/**
*@author kevin.wangzhiqiang
*@time 2019/12/26 15:18
*desc
*/
public abstract class RxResourceObserver<T extends BaseResponse> extends ResourceObserver<T> {

    private BaseView mView;
    private String msg;
    private boolean showLoading;


    public RxResourceObserver(BaseView mView) {
        this(mView, true);
    }

    public RxResourceObserver(BaseView mView, String msg, boolean showLoading) {
        this.mView = mView;
        this.msg = msg;
        this.showLoading = showLoading;
    }

    public RxResourceObserver(BaseView mView, boolean showLoading) {
        this(mView, null, showLoading);
    }


    @Override
    protected void onStart() {
        // TODO 在这里可以添加请求网络前的一些初始化操作,比如打开请求网络的loading
        if (showLoading && mView != null) {
            mView.showLoading(msg);
            mView.hideContent();
        }
    }

    @Override
    public void onComplete() {
        if (mView != null) {
            mView.hideLoading();
            mView.hideNetError();
            mView.showContent();
        }
    }

    @Override
    public void onNext(T t) {
        if (t.getCode() == 200) {
            _onNext(t);
        } else {
            ToastUtils.show(t.getMessage());
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (mView != null) {
            mView.hideLoading();
        }
        //网络
        if (!NetUtil.checkNet()) {
            if (mView != null) {
                mView.showNetError();
            }
            _onError(AppApplication.getContext().getString(R.string.no_net));
            ToastUtils.show(AppApplication.getContext().getString(R.string.no_net));
        }
        //服务器
        else if (e instanceof ServerException) {
            _onError(e.getMessage());
        }
        //其它
        else {
            _onError(AppApplication.getContext().getString(R.string.net_error));
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}
