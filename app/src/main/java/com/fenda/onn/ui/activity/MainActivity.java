package com.fenda.onn.ui.activity;

import android.util.Log;
import com.fenda.onn.R;
import com.fenda.onn.bean.WeatherBean;
import com.fenda.onn.common.base.BaseMvpActivity;
import com.fenda.onn.contract.MainContract;
import com.fenda.onn.ui.presenter.MainPresenter;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.IMainView {

    @Override public int onBindLayout() {
        return R.layout.activity_main2;
    }

    @Override public void initView() {
        mPresenter.processWeather();
    }

    @Override protected void initPresenter() {
        mPresenter.attachView(this);
    }

    @Override public void showErrorTip(String msg) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void message(WeatherBean bean){
        Log.e("TAG",bean.getWeatherinfo().toString());
    }
}
