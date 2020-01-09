package com.fenda.onn.ui.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fenda.onn.R;
import com.fenda.onn.bean.FmStationBean;

import java.util.List;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/6
 * @Describe: FM收藏列表适配器
 */
public class FmCollectListAdapter extends BaseQuickAdapter<FmStationBean, BaseViewHolder> {
    public FmCollectListAdapter(int layoutResId, @Nullable List<FmStationBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FmStationBean item) {
            helper.setText(R.id.tv_fm_collect_rate, item.getStationName());
            helper.setBackgroundRes(R.id.bt_fm_cancel, R.mipmap.icon_cancel);
            helper.setBackgroundRes(R.id.bt_collect_compile, R.mipmap.icon_compile);
            helper.addOnClickListener(R.id.bt_collect_compile);
            helper.addOnClickListener(R.id.bt_fm_cancel);

    }
}

