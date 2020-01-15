package com.fenda.onn.ui.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenda.onn.R;
import com.fenda.onn.common.base.BaseMvpActivity;
import com.fenda.onn.utils.PopupWindowUtil;
import com.fenda.onn.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author kevin.wangzhiqiang
 * @time 2019/12/26 15:19
 * desc  首页
 */
public class MainActivity extends BaseMvpActivity {

    @BindView(R.id.tvLightDj)
    TextView mTvLightDj;
    @BindView(R.id.tvFm)
    TextView mTvFmRadio;
    @BindView(R.id.tvDeviceName)
    TextView mTvDviceName;
    @BindView(R.id.ivEdit)
    ImageView mImgUpdateDeviceName;
    @BindView(R.id.ivDevice_second)
    ImageView mImgDeviceSecond;
    @BindView(R.id.tv_Device_Second_Name)
    TextView mTvDeviceSecondName;
    @BindView(R.id.tvAgreement)
    TextView mTvAgreement;

    private View mTwsPopupWindowLayout;
    private View mRgUpdatePopupWindowLayout;
    private View mResetConnectPopupWindowLayout;
    private TextView mTvTwsTitle;
    private TextView mTvTwsContent;
    private TextView mTvCancelTws;
    private TextView mTvCancelReset;
    private Button mBtConnectTws;
    private Button mBtCancel;
    private Button mBtConfirm;
    private Button mBtResetConnect;
    private EditText mEtDeviceName;
    private boolean isConnectTws = false;

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

    @OnClick({R.id.tvLightDj, R.id.tvLightMode, R.id.tvFm, R.id.ivSetting,
            R.id.ivTws, R.id.ivEdit, R.id.tvAgreement})
    public void handleClickEvent(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tvLightDj:
                intent.setClass(mContext, LightDjActivity.class);
                startActivity(intent);
                break;
            case R.id.tvLightMode:
                intent.setClass(mContext, LightModelActivity.class);
                startActivity(intent);
                break;
            case R.id.tvFm:
                intent.setClass(mContext, FmRadioActivity.class);
                startActivity(intent);
                break;
            case R.id.ivSetting:
                intent.setClass(mContext, AboutDeviceActivity.class);
                startActivity(intent);
                break;
            case R.id.tvAgreement:
                intent.setClass(mContext, PrivacyPolicyActivity.class);
                startActivity(intent);
                break;
            case R.id.ivTws:
                showTwsPopupWindow();
                break;
            case R.id.ivEdit:
                showUpdateDeviceNamePopupWindow();
                break;
            default:
                break;
        }
    }

    /**
     * 打开修改备注弹窗
     */
    public void showUpdateDeviceNamePopupWindow() {
        initUpdateDeviceNamePopupWindow();
        PopupWindowUtil.createPopupWindow(this, mRgUpdatePopupWindowLayout,
                true, true,
                false, Gravity.CENTER);
    }

    /**
     * 初始化修改备注PopupWindow相关参数
     */
    public void initUpdateDeviceNamePopupWindow() {
        mRgUpdatePopupWindowLayout = View.inflate(mContext, R.layout.ppw_update_device_name_layout, null);
        mBtCancel = mRgUpdatePopupWindowLayout.findViewById(R.id.rb_cancel);
        mBtConfirm = mRgUpdatePopupWindowLayout.findViewById(R.id.rb_confirm);
        mEtDeviceName = mRgUpdatePopupWindowLayout.findViewById(R.id.clearWriteEditText);
        mBtCancel.setOnClickListener(v -> {
            closePopPopupWindow();
        });
        mBtConfirm.setOnClickListener(v -> {
            String newDeviceName = mEtDeviceName.getText().toString().trim();
            mTvDviceName.setText(newDeviceName);
            closePopPopupWindow();
        });
    }

    /**
     * 打开TWS弹窗
     */
    public void showTwsPopupWindow() {
        initTwsPopupWindow();
        mBtConnectTws.setEnabled(true);
        PopupWindowUtil.createPopupWindow(this, mTwsPopupWindowLayout,
                true, true,
                false, Gravity.CENTER);
    }

    /**
     * 初始化TWS PopupWindow相关参数
     */
    public void initTwsPopupWindow() {
        //TWS对话框布局
        mTwsPopupWindowLayout = View.inflate(mContext, R.layout.ppw_device_tws_layout, null);
        mTvTwsTitle = mTwsPopupWindowLayout.findViewById(R.id.tv_tws_title);
        mTvTwsContent = mTwsPopupWindowLayout.findViewById(R.id.tv_tws_content);
        mTvCancelTws = mTwsPopupWindowLayout.findViewById(R.id.tv_tws_cancel);
        mBtConnectTws = mTwsPopupWindowLayout.findViewById(R.id.bt_tws_connect);
        mBtConnectTws.setOnClickListener(v -> {
            mBtConnectTws.setEnabled(false);
            if (isConnectTws) {
                //关闭tws操作
                showDeviceSecond(false);
                isConnectTws = false;
            } else {
                //打开tws操作
                showDeviceSecond(true);
                isConnectTws = true;
            }
            closePopPopupWindow();
        });
        mTvCancelTws.setOnClickListener(v -> {
            closePopPopupWindow();
        });
        //初始化数据
        if (isConnectTws == false) {
            mTvTwsTitle.setText(R.string.tws_connect_title);
            mTvTwsContent.setText(R.string.tws_connect_content);
            mBtConnectTws.setText(R.string.tws_connect_button);
            mTvCancelTws.setText(R.string.tws_connect_cancel);
        } else {

            mTvTwsTitle.setText(R.string.tws_disconnect_title);
            mTvTwsContent.setText(R.string.tws_disconnect_content);
            mBtConnectTws.setText(R.string.tws_disconnect_button);
            mTvCancelTws.setText(R.string.tws_disconnect_cancel);
        }
    }

    /**
     * 打开重连弹窗
     */
    public void showResetConnectPopupWindow() {
        initResetConnectPopupWindow();
        PopupWindowUtil.createPopupWindow(this, mResetConnectPopupWindowLayout,
                false, true,
                false, Gravity.CENTER);
    }

    /**
     * 初始化重连 PopupWindow相关参数
     */
    public void initResetConnectPopupWindow() {
        //TWS对话框布局
        mResetConnectPopupWindowLayout = View.inflate(mContext, R.layout.ppw_device_disconnect_layout, null);
        mBtResetConnect = mResetConnectPopupWindowLayout.findViewById(R.id.bt_re_connect);
        mTvCancelReset = mResetConnectPopupWindowLayout.findViewById(R.id.tv_cancel);
        mTvCancelReset.setOnClickListener(v -> {
            ToastUtils.show("重连");
            closePopPopupWindow();
        });
        mBtResetConnect.setOnClickListener(v -> {
            closePopPopupWindow();
        });
    }

    /**
     * 显示TWS设备
     *
     * @param isShow
     */
    public void showDeviceSecond(boolean isShow) {
        if (isShow) {
            mImgDeviceSecond.setVisibility(View.VISIBLE);
            mTvDeviceSecondName.setVisibility(View.VISIBLE);
        } else {
            mImgDeviceSecond.setVisibility(View.GONE);
            mTvDeviceSecondName.setVisibility(View.GONE);
        }
    }

    /**
     * 关闭弹窗
     */
    public void closePopPopupWindow() {
        PopupWindowUtil.closePopPopupWindow();
    }
}
