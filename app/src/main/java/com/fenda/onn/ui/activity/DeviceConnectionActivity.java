package com.fenda.onn.ui.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fenda.onn.R;
import com.fenda.onn.bean.DeviceConnectionBean;
import com.fenda.onn.common.base.BaseActivity;
import com.fenda.onn.ui.adapter.DeviceConnectionAdapter;
import com.fenda.onn.utils.PopupWindowUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author kevin.wangzhiqiang
 * @time 2019/12/30 17:18
 * desc  设备连接界面
 */
public class DeviceConnectionActivity extends BaseActivity {
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rvContent)
    RecyclerView rvContent;
    View mConnectPopupWindowLayout;
    Button mBtCancelConnect;
    private List<DeviceConnectionBean> mDatas;
    private DeviceConnectionAdapter mAdapter;
    private PopupWindow mPopupWindow;

    @Override
    public int onBindLayout() {
        return R.layout.activity_device_connection;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void initData() {
        super.initData();
        mDatas = new ArrayList<>();
        DeviceConnectionBean bean1 = new DeviceConnectionBean(R.mipmap.icon_device_connect, mContext.getString(R.string.device_name), mContext.getString(R.string.connect));
        DeviceConnectionBean bean2 = new DeviceConnectionBean(R.mipmap.icon_device_connect, mContext.getString(R.string.device_name), mContext.getString(R.string.connect));
        mDatas.add(bean1);
        mDatas.add(bean2);
        mAdapter = new DeviceConnectionAdapter(mDatas);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);

    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                initConnectPopupWindow();
                startActivity(new Intent(mContext, MainActivity.class));
            }
        });
    }


    @Override
    public boolean isToolbarImmersion() {
        return true;
    }


    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        closePopPopupWindow();
    }

    /**
     * 初始化连接对话框
     */
    public void initConnectPopupWindow() {
        mConnectPopupWindowLayout = View.inflate(mContext, R.layout.layout_connect_dialog, null);
        mBtCancelConnect = mConnectPopupWindowLayout.findViewById(R.id.bt_connect);
        PopupWindowUtil.createPopupWindow(this, mConnectPopupWindowLayout, true, true,
                false, Gravity.CENTER);
        mBtCancelConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindowUtil.closePopPopupWindow();
            }
        });
    }

    /**
     * 关闭弹窗
     */
    public void closePopPopupWindow() {
        PopupWindowUtil.closePopPopupWindow();
    }
}
