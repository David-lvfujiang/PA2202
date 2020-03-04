package com.fenda.onn.ui.activity;

import android.widget.TextView;
import butterknife.BindView;
import com.fenda.onn.R;
import com.fenda.onn.bean.WeatherBean;
import com.fenda.onn.common.base.BaseMvpActivity;
import com.fenda.onn.contract.MainContract;
import com.fenda.onn.ui.presenter.MainPresenter;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.IMainView {
    @BindView(R.id.tv_weather)
    TextView mTvWeather;

    @Override public int onBindLayout() {
        return R.layout.activity_main;
    }

    @Override public void initView() {
        mPresenter.processWeather();
    }

    @Override protected void initPresenter() {
        mPresenter.attachView(this);
    }

    @Override public void showErrorTip(String msg) {
    }



    /**
     * 接收后台参数
     *
     * @param weatherBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleWeatherMessage(WeatherBean weatherBean) {
        mTvWeather.setText(weatherBean.getWeatherinfo().toString());
    }
}
