package com.fenda.onn.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.fenda.onn.R;
import com.fenda.onn.common.base.BaseActivity;
import com.fenda.onn.utils.ToastUtils;

import butterknife.OnClick;

/**
 * @author David-lvfujiang
 * @time 2019/12/30 17:18
 * desc  关于设备界面
 */
public class AboutDeviceActivity extends BaseActivity {
    private PopupWindow mPopupWindow;
    private View mBottomView;
    private RadioGroup mRgLanguage;
    private Button mBtCancel;
    private Button mBtConfirm;

    @Override
    public int onBindLayout() {
        return R.layout.activity_about_device;
    }

    @Override
    public void initView() {
    }

    @Override
    public boolean isToolbarImmersion() {
        return true;
    }

    @OnClick({R.id.bt_version_update, R.id.tv_language, R.id.tv_privacy, R.id.ivBack})
    public void handleClickEvent(View view) {
        switch (view.getId()) {
            case R.id.bt_version_update:
                ToastUtils.show("更新");
                break;
            case R.id.tv_language:
                initLanguagePopuWindow();
                showLanguagePopupWindow();
                break;
            case R.id.tv_privacy:
                ToastUtils.show("隐私条款");
                break;
            case R.id.ivBack:
                finish();
                break;
            default:
                break;
        }
    }

    public void initLanguagePopuWindow() {
        //底部对话框布局
        mBottomView = View.inflate(mContext, R.layout.layout_device_about_dialog, null);
        mRgLanguage = mBottomView.findViewById(R.id.rg_language);
        mBtCancel = mBottomView.findViewById(R.id.bt_cancel);
        mBtConfirm = mBottomView.findViewById(R.id.bt_confirm);
        mRgLanguage.setOnCheckedChangeListener((group, checkId) -> {
            switch (checkId) {
                case R.id.rb_chinese:
                    ToastUtils.show("简体中文");
                    break;
                case R.id.rb_english:
                    ToastUtils.show("英文");
                    break;
                default:
                    break;
            }
        });
        mBtConfirm.setOnClickListener(v -> {
            ToastUtils.show("确定");
            closeLanguagePopPopupWindow();
        });
        mBtCancel.setOnClickListener(v -> {
            ToastUtils.show("取消");
            closeLanguagePopPopupWindow();
        });
    }

    /**
     * 打开PopupWindow语言弹窗弹窗
     */
    private void showLanguagePopupWindow() {
        mPopupWindow = new PopupWindow(mBottomView, -1, -2);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        //设置弹窗位置
        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 关闭弹窗
     */
    public void closeLanguagePopPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }
}
