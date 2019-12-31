package com.fenda.onn.ui.fragment;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fenda.onn.R;
import com.fenda.onn.bean.ProductionSeriesBean;
import com.fenda.onn.common.base.BaseFragment;
import com.fenda.onn.config.Constant;
import com.fenda.onn.ui.activity.ProductionTypeActivity;
import com.fenda.onn.ui.adapter.ProductionSeriesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author kevin.wangzhiqiang
 * @time 2019/12/30 11:45
 * desc  音响系列
 */
public class SoundFragment extends BaseFragment {

    @BindView(R.id.rvContent)
    RecyclerView rvContent;
    private List<ProductionSeriesBean> mDatas;
    private ProductionSeriesAdapter mAdapter;


    @Override
    public int onBindLayout() {
        return R.layout.fragment_sound_series;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        super.initData();
        mDatas = new ArrayList<>();
        ProductionSeriesBean bean1 = new ProductionSeriesBean(R.mipmap.pic_party, mContext.getString(R.string.party_boom));
        ProductionSeriesBean bean2 = new ProductionSeriesBean(R.mipmap.pic_indoor, mContext.getString(R.string.indoor_speaker));
        ProductionSeriesBean bean3 = new ProductionSeriesBean(R.mipmap.pic_rugged, mContext.getString(R.string.rugged_speaker));
        mDatas.add(bean1);
        mDatas.add(bean2);
        mDatas.add(bean3);
        mAdapter = new ProductionSeriesAdapter( mDatas);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, ProductionTypeActivity.class);
                intent.putExtra(Constant.Intent.PIC_URL, mDatas.get(position).getImgResId());
                startActivity(intent);
            }
        });
    }
}
