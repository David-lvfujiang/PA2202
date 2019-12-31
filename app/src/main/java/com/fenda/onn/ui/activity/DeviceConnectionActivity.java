package com.fenda.onn.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fenda.onn.R;
import com.fenda.onn.bean.DeviceConnectionBean;
import com.fenda.onn.common.base.BaseActivity;
import com.fenda.onn.ui.adapter.DeviceConnectionAdapter;

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
    private List<DeviceConnectionBean> mDatas;
    private DeviceConnectionAdapter mAdapter;

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
        mAdapter = new DeviceConnectionAdapter( mDatas);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);

    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
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
}
