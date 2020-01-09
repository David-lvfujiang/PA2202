package com.fenda.onn.ui.adapter;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fenda.onn.R;
import com.fenda.onn.bean.FmStationBean;

import java.util.HashSet;
import java.util.List;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/6
 * @Describe: FM搜索列表适配器
 */
public class FmSearchListAdapter extends BaseQuickAdapter<FmStationBean, BaseViewHolder> {
    public FmSearchListAdapter(int layoutResId, @Nullable List<FmStationBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, FmStationBean item) {
        helper.setText(R.id.tv_fm_rate, item.getStationName());
        helper.addOnClickListener(R.id.rb_fm_love);
        helper.addOnClickListener(R.id.bt_compile);
    }
}
