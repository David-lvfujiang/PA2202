package com.fenda.onn.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

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
public class ProductionTypeActivity extends BaseActivity implements View.OnClickListener {
    private final int POPUPWINDOW_WIDTH = 900;
    private final int POPUPWINDOW_HIGH = -2;
    @BindView(R.id.ivHead)
    ImageView ivHead;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvContent)
    RecyclerView rvContent;
    Button mBtCancel, mBtAgree;
    private List<ProductionTypeBean> mDatas;
    private ProductionTypeAdapter mAdapter;
    private View mPrivacyPolicyPopupWindowLayout;
    private PopupWindow mPopupWindow;

    @Override
    public int onBindLayout() {
        return R.layout.activity_production_type;
    }

    @Override
    public void initView() {
        mPrivacyPolicyPopupWindowLayout = View.inflate(mContext, R.layout.layout_privacy_policy_dialog, null);
        mBtAgree = mPrivacyPolicyPopupWindowLayout.findViewById(R.id.bt_agree);
        mBtCancel = mPrivacyPolicyPopupWindowLayout.findViewById(R.id.bt_cancel);
        mBtAgree.setOnClickListener(this);
        mBtCancel.setOnClickListener(this);
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
                createPrivacyPolicyPopupWindow();
            }
        });
    }

    @OnClick({R.id.ivBack})
    public void onViewClicked() {
        finish();
    }

    public void createPrivacyPolicyPopupWindow() {
        createPopupWindow(mPrivacyPolicyPopupWindowLayout, POPUPWINDOW_WIDTH, POPUPWINDOW_HIGH, 0, 0, true);
    }

    /**
     * @param view    布局文件
     * @param width   宽度
     * @param high    高度
     * @param offsetX X轴偏移量
     * @param offsetY Y轴偏移量
     * @param isFocus 点击外部是否关闭
     */
    public void createPopupWindow(View view, int width, int high, int offsetX, int offsetY, boolean isFocus) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        Log.e("TAG", "widthPixels = " + widthPixels + ",heightPixels = " + heightPixels);
        mPopupWindow = new PopupWindow(view, (int) (widthPixels * 0.9), (int) (heightPixels * 0.8), true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(isFocus);
        mPopupWindow.setFocusable(isFocus);
        //设置背景为半透明
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        //监听PopupWindow关闭时将透明度设置成原来
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        //设置弹窗位置PopupWindow的相关参数
        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, offsetX, offsetY);
    }

    /**
     * 关闭弹窗
     */
    public void closePopPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_agree:
                closePopPopupWindow();
                startActivity(new Intent(mContext, DeviceConnectionActivity.class));
                break;
            case R.id.bt_cancel:
                closePopPopupWindow();
            default:
                break;
        }
    }
}
