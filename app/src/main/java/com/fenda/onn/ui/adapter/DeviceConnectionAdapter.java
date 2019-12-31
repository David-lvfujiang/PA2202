package com.fenda.onn.ui.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fenda.onn.R;
import com.fenda.onn.bean.DeviceConnectionBean;

import java.util.List;

public class DeviceConnectionAdapter extends BaseQuickAdapter<DeviceConnectionBean, BaseViewHolder> {
    public DeviceConnectionAdapter(@Nullable List<DeviceConnectionBean> data) {
        super(R.layout.item_device_connection, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceConnectionBean item) {
        helper.setText(R.id.tvName, item.getName());
        helper.setImageResource(R.id.ivPic, item.getDeviceResId());
        helper.setText(R.id.tvConnect, item.getConnectStatus());
        helper.addOnClickListener(R.id.tvConnect);

    }
}
