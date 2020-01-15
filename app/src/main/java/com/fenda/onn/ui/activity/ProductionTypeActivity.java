package com.fenda.onn.ui.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fenda.onn.R;
import com.fenda.onn.bean.ProductionTypeBean;
import com.fenda.onn.common.base.BaseActivity;
import com.fenda.onn.config.Constant;
import com.fenda.onn.ui.adapter.ProductionTypeAdapter;
import com.fenda.onn.utils.PopupWindowUtil;

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
    private  Button mBtCancel, mBtAgree;
    private List<ProductionTypeBean> mDatas;
    private ProductionTypeAdapter mAdapter;
    private View mPrivacyPolicyPopupWindowLayout;

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
        mAdapter.setOnProductionTypeItemClickListener(bean -> {
            createPrivacyPolicyPopupWindow();
        });
    }

    @OnClick({R.id.ivBack})
    public void onViewClicked() {
        finish();
    }

    public void createPrivacyPolicyPopupWindow() {
        initPrivacyPolicyPopupWindow();
        PopupWindowUtil.createPopupWindow(this, mPrivacyPolicyPopupWindowLayout,
                true, true,
                true, Gravity.CENTER);
    }

    public void initPrivacyPolicyPopupWindow() {
        mPrivacyPolicyPopupWindowLayout = View.inflate(mContext, R.layout.ppw_privacy_policy_layout, null);
        mBtAgree = mPrivacyPolicyPopupWindowLayout.findViewById(R.id.bt_agree);
        mBtCancel = mPrivacyPolicyPopupWindowLayout.findViewById(R.id.bt_cancel);
        mBtAgree.setOnClickListener(v -> {
            closePopPopupWindow();
            startActivity(new Intent(mContext, DeviceConnectionActivity.class));
        });
        mBtCancel.setOnClickListener(v -> {
            closePopPopupWindow();
        });
    }

    /**
     * 关闭弹窗
     */
    public void closePopPopupWindow() {
        PopupWindowUtil.closePopPopupWindow();
    }

}
