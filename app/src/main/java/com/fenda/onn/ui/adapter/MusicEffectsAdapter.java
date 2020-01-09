package com.fenda.onn.ui.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fenda.onn.R;
import com.fenda.onn.bean.MusicEffectsBean;

import java.util.List;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/2
 * @Describe: 音效库适配器
 */
public class MusicEffectsAdapter extends BaseQuickAdapter<MusicEffectsBean, BaseViewHolder> {

    public MusicEffectsAdapter(int layoutResId, @Nullable List<MusicEffectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicEffectsBean item) {
        helper.setText(R.id.item_bt_miusic_effects, item.getEffectsName());
        helper.setBackgroundRes(R.id.item_bt_miusic_effects, item.getImgResId());
        helper.addOnClickListener(R.id.item_bt_miusic_effects);
    }
}