package com.fenda.onn.ui.presenter;

import com.fenda.onn.bean.WeatherBean;
import com.fenda.onn.common.base.BaseMvpPresenter;
import com.fenda.onn.config.Constant;
import com.fenda.onn.contract.MainContract;
import com.fenda.onn.http.ApiService;
import com.fenda.onn.http.RetrofitHelper;
import com.fenda.onn.http.base.BaseObserver;
import com.fenda.onn.http.base.BaseResponse;
import com.fenda.onn.http.exception.RetrofitException;
import com.fenda.onn.utils.ToastUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Date : 2020/3/4
 * Author : Davaid.lvfujiang
 * Desc : 请求天气
 */
public class MainPresenter extends BaseMvpPresenter<MainContract.IMainView> implements MainContract.IMainPresenter {
    @Override public void processWeather() {
        RetrofitHelper retrofitHelper = RetrofitHelper.getInstance(Constant.Sevice.SEVICE_URL);
        ApiService service = retrofitHelper.getServer(ApiService.class);
        Observable<BaseResponse<WeatherBean>> observable = service.getWeatherInfo("101010100");
        mViewRef.get().showLoading("正在加载");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<WeatherBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override public void onNext(BaseResponse<WeatherBean> weatherBeanBaseResponse) {
                        if (weatherBeanBaseResponse.getCode() == 200) {
                            ToastUtils.show("请求成功");
                            mViewRef.get().hideLoading();
                        } else {
                            ToastUtils.show(weatherBeanBaseResponse.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override public void onError(RetrofitException.ResponeThrowable responeThrowable) {
                        System.out.println(responeThrowable.toString());
                        mViewRef.get().hideLoading();
                        switch (responeThrowable.code) {
                            case RetrofitException.ERROR.UNKNOWN:
                                mViewRef.get().showErrorTip("未知错误");
                                break;
                            case RetrofitException.ERROR.PARSE_ERROR:
                                ToastUtils.show("解析错误");
                                break;
                            case RetrofitException.ERROR.NETWORD_ERROR:
                                mViewRef.get().showNetError();
                                ToastUtils.show(responeThrowable.message);
                                break;
                            case RetrofitException.ERROR.HTTP_ERROR:
                                ToastUtils.show("协议错误");
                                break;
                            case RetrofitException.ERROR.SSL_ERROR:
                                ToastUtils.show("证书错误");
                                break;
                        }
                    }
                });
    }
}
