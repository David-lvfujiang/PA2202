package com.fenda.onn.ui.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fenda.onn.R;
import com.fenda.onn.bean.ProductionSeriesBean;

import java.util.List;

public class ProductionSeriesAdapter extends BaseQuickAdapter<ProductionSeriesBean, BaseViewHolder> {
    public ProductionSeriesAdapter(@Nullable List<ProductionSeriesBean> data) {
        super(R.layout.item_production_series, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, ProductionSeriesBean item) {
        helper.setText(R.id.tvName,item.getName());
        helper.setImageResource(R.id.ivPic,item.getImgResId());
    }

}
