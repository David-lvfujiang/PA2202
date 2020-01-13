package com.fenda.onn.ui.activity;

import android.view.View;
import android.widget.CompoundButton;

import com.fenda.onn.R;
import com.fenda.onn.common.base.BaseActivity;
import com.fenda.onn.utils.ToastUtils;

import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class LightModelActivity extends BaseActivity {

    @Override
    public int onBindLayout() {
        return R.layout.activity_light_model;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.ivBack, R.id.bt_style_one, R.id.bt_style_two, R.id.bt_style_three,
            R.id.bt_style_four, R.id.bt_style_five})
    public void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.bt_style_one:
                ToastUtils.show("风格1");
                break;
            case R.id.bt_style_two:
                ToastUtils.show("风格2");
                break;
            case R.id.bt_style_three:
                ToastUtils.show("风格3");
                break;
            case R.id.bt_style_four:
                ToastUtils.show("风格4");
                break;
            case R.id.bt_style_five:
                ToastUtils.show("风格5");
                break;
            default:
                break;
        }
    }

    @OnCheckedChanged({R.id.cb_strip, R.id.cb_marquee})
    public void checkBoxChangeEvent(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.cb_strip:
                if (ischanged) {
                    ToastUtils.show("打开灯带");
                }
                break;
            case R.id.cb_marquee:
                if (ischanged) {
                    ToastUtils.show("打开跑马灯");
                }
                break;
            default:
                break;
        }
    }
}
