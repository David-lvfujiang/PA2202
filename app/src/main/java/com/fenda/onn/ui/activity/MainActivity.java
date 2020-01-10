package com.fenda.onn.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fenda.onn.R;
import com.fenda.onn.common.base.BaseMvpActivity;
import com.fenda.onn.config.Constant;
import com.fenda.onn.utils.DensityUtil;
import com.fenda.onn.utils.LogUtils;
import com.fenda.onn.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author kevin.wangzhiqiang
 * @time 2019/12/26 15:19
 * desc  首页
 */
public class MainActivity extends BaseMvpActivity implements View.OnClickListener {
    private final int POPUPWINDOW_WIDTH = 900;
    private final int POPUPWINDOW_HIGH = -2;
    private final int POPUPWINDOW_OFFSET = -200;
    private Context mContext = this;
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
    TextView mTVDeviceSecondName;
    @BindView(R.id.tvAgreement)
    TextView mTvAgreement;

    private PopupWindow mPopupWindow;
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
    boolean isConnectTws = false;

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

    @OnClick({R.id.tvLightDj, R.id.tvFm, R.id.ivSetting, R.id.ivTws, R.id.ivEdit, R.id.tvAgreement})
    public void handleClickEvent(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tvLightDj:
                intent.setClass(mContext, LightDjActivity.class);
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
        createPopupWindow(mRgUpdatePopupWindowLayout, POPUPWINDOW_WIDTH, POPUPWINDOW_HIGH, 0, 0, true);
    }

    /**
     * 初始化修改备注PopupWindow相关参数
     */
    public void initUpdateDeviceNamePopupWindow() {
        mRgUpdatePopupWindowLayout = View.inflate(mContext, R.layout.layout_device_name_dialog, null);
        mBtCancel = mRgUpdatePopupWindowLayout.findViewById(R.id.rb_cancel);
        mBtConfirm = mRgUpdatePopupWindowLayout.findViewById(R.id.rb_confirm);
        mEtDeviceName = mRgUpdatePopupWindowLayout.findViewById(R.id.clearWriteEditText);
        mBtCancel.setOnClickListener(this);
        mBtConfirm.setOnClickListener(this);
    }

    /**
     * 打开TWS弹窗
     */
    public void showTwsPopupWindow() {
        initTwsPopupWindow();
        mBtConnectTws.setEnabled(true);
        createPopupWindow(mTwsPopupWindowLayout, POPUPWINDOW_WIDTH, POPUPWINDOW_HIGH, 0, 0, true);
    }

    /**
     * 初始化TWS PopupWindow相关参数
     */
    public void initTwsPopupWindow() {
        //TWS对话框布局
        mTwsPopupWindowLayout = View.inflate(mContext, R.layout.layout_device_tws_dialog, null);
        mTvTwsTitle = mTwsPopupWindowLayout.findViewById(R.id.tv_tws_title);
        mTvTwsContent = mTwsPopupWindowLayout.findViewById(R.id.tv_tws_content);
        mTvCancelTws = mTwsPopupWindowLayout.findViewById(R.id.tv_tws_cancel);
        mBtConnectTws = mTwsPopupWindowLayout.findViewById(R.id.bt_tws_connect);
        mBtConnectTws.setOnClickListener(this);
        mTvCancelTws.setOnClickListener(this);
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
        createPopupWindow(mResetConnectPopupWindowLayout, POPUPWINDOW_WIDTH, POPUPWINDOW_HIGH, 0, 0, false);
    }

    /**
     * 初始化重连 PopupWindow相关参数
     */
    public void initResetConnectPopupWindow() {
        //TWS对话框布局
        mResetConnectPopupWindowLayout = View.inflate(mContext, R.layout.layout_device_disconnect_dialog, null);
        mBtResetConnect = mResetConnectPopupWindowLayout.findViewById(R.id.bt_re_connect);
        mTvCancelReset = mResetConnectPopupWindowLayout.findViewById(R.id.tv_cancel);
        mTvCancelReset.setOnClickListener(this);
        mBtResetConnect.setOnClickListener(this);
    }

    /**
     * 创建PopupWindow
     *
     * @param view    布局文件
     * @param width   宽度
     * @param high    高度
     * @param offsetX X轴偏移量
     * @param offsetY Y轴偏移量
     * @param isFocus 点击外部是否关闭
     */
    public void createPopupWindow(View view, int width, int high, int offsetX, int offsetY, boolean isFocus) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        LogUtils.e("TAG", "widthPixels = " + widthPixels + ",heightPixels = " + heightPixels);
        mPopupWindow = new PopupWindow(view, (int) (widthPixels * 0.8), -2, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(isFocus);
        mPopupWindow.setFocusable(isFocus);
        //设置背景为半透明
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        //监听PopupWindow关闭时将透明度设置成原来
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        //设置弹窗位置PopupWindow的相关参数
        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, offsetX, offsetY);
    }

    /**
     * PopupWindow点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_tws_connect:
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
                break;
            case R.id.rb_confirm:
                String newDeviceName = mEtDeviceName.getText().toString().trim();
                if (newDeviceName != null && "".equals(newDeviceName)) {
                    mTvDviceName.setText(newDeviceName);
                    closePopPopupWindow();
                }
                break;
            case R.id.bt_re_connect:
                ToastUtils.show("重连");
                closePopPopupWindow();
                break;
            case R.id.tv_tws_cancel:
            case R.id.rb_cancel:
            case R.id.tv_cancel:
                closePopPopupWindow();
                break;
            default:
                break;
        }
    }

    /**
     * 显示TWS设备
     *
     * @param isShow
     */
    public void showDeviceSecond(boolean isShow) {
        if (isShow) {
            mImgDeviceSecond.setVisibility(View.VISIBLE);
            mTVDeviceSecondName.setVisibility(View.VISIBLE);
        } else {
            mImgDeviceSecond.setVisibility(View.GONE);
            mTVDeviceSecondName.setVisibility(View.GONE);
        }
    }

    /**
     * 关闭弹窗
     */
    public void closePopPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }
}
