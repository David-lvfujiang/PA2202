package com.fenda.onn.ui.activity;

import com.fenda.onn.R;
import com.fenda.onn.common.base.BaseMvpActivity;
import com.fenda.onn.utils.DensityUtil;
import com.fenda.onn.utils.ToastUtils;

/**
 * @author kevin.wangzhiqiang
 * @time 2019/12/26 15:19
 * desc  首页
 */
public class MainActivity extends BaseMvpActivity {


    @Override
    public int onBindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void showErrorTip(String msg) {

    }
}
