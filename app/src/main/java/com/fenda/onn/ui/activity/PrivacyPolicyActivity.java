package com.fenda.onn.ui.activity;

import android.view.View;

import com.fenda.onn.R;
import com.fenda.onn.common.base.BaseActivity;

import butterknife.OnClick;

/**
 * @author kevin.wangzhiqiang
 * @time 2019/12/26 15:19
 * desc  隐私条款界面
 */
public class PrivacyPolicyActivity extends BaseActivity {

    @Override
    public boolean isToolbarImmersion() {
        return true;
    }

    @Override
    public int onBindLayout() {
        return R.layout.activity_privacy_policy;
    }

    @Override
    public void initView() {
    }

    @OnClick({R.id.ivBack, R.id.bt_agree})
    public void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.bt_agree:
                finish();
                break;
            default:
                break;
        }
    }
}
