package com.fenda.onn.ui.activity;

import android.content.Intent;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fenda.onn.R;
import com.fenda.onn.bean.ProductionTypeBean;
import com.fenda.onn.common.base.BaseActivity;
import com.fenda.onn.config.Constant;
import com.fenda.onn.ui.adapter.ProductionTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author kevin.wangzhiqiang
 * @time 2019/12/30 16:21
 * desc  产品类型界面
 */
public class ProductionTypeActivity extends BaseActivity {
    @BindView(R.id.ivHead)
    ImageView ivHead;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvContent)
    RecyclerView rvContent;
    private List<ProductionTypeBean> mDatas;
    private ProductionTypeAdapter mAdapter;

    @Override
    public int onBindLayout() {
        return R.layout.activity_production_type;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void initData() {
        super.initData();
        // 设置顶部图片
        int picResId = getIntent().getIntExtra(Constant.Intent.PIC_URL, 0);
        ivHead.setImageResource(picResId);
        mDatas = new ArrayList<>();
        ProductionTypeBean bean1 = new ProductionTypeBean(R.mipmap.icon_device, mContext.getString(R.string.device_name));
        ProductionTypeBean bean2 = new ProductionTypeBean(R.mipmap.icon_device, mContext.getString(R.string.device_name));
        mDatas.add(bean1);
        mDatas.add(bean2);
        mAdapter = new ProductionTypeAdapter(mContext, mDatas);
        rvContent.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvContent.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnProductionTypeItemClickListener(new ProductionTypeAdapter.OnProductionTypeItemClickListener() {
            @Override
            public void onProductionTypeItemClick(ProductionTypeBean bean) {
                startActivity(new Intent(mContext,DeviceConnectionActivity.class));
            }
        });
    }

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        finish();
    }
}
