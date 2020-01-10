package com.fenda.onn.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fenda.onn.R;
import com.fenda.onn.bean.FmStationBean;
import com.fenda.onn.common.base.BaseFragment;
import com.fenda.onn.ui.adapter.FmCollectListAdapter;
import com.fenda.onn.ui.view.ClearWriteEditText;
import com.fenda.onn.utils.LogUtils;
import com.fenda.onn.utils.ToastUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/10
 * @Describe: FM收藏列表
 */
public class FmCollectListFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.rv_fm_collect)
    RecyclerView mRvCollect;
    static List<FmStationBean> mListFmStations = new ArrayList<FmStationBean>();
    private FmCollectListAdapter fmCollectListAdapter;
    private int index;
    private FmStationBean fmStationBean;
    private View mFmPopupWindowLayout;
    private PopupWindow mPopupWindow;
    private String FmRemark;
    private ClearWriteEditText mClearWriteEditText;
    private Button mBtCancel, mBtConfirm;

    @Override
    public int onBindLayout() {
        return R.layout.fragment_fm_collect;
    }

    @Override
    protected void initView() {
        mFmPopupWindowLayout = View.inflate(getActivity(), R.layout.layout_fm_dialog, null);
        mClearWriteEditText = mFmPopupWindowLayout.findViewById(R.id.clearWriteEditText);
        mBtConfirm = mFmPopupWindowLayout.findViewById(R.id.rb_confirm_update);
        mBtCancel = mFmPopupWindowLayout.findViewById(R.id.rb_cancel_update);
        mBtConfirm.setOnClickListener(this);
        mBtCancel.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mListFmStations = (ArrayList<FmStationBean>) LitePal.findAll(FmStationBean.class);
        fmCollectListAdapter = new FmCollectListAdapter(R.layout.item_fm_collect, mListFmStations);
        fmCollectListAdapter.openLoadAnimation();
        fmCollectListAdapter.setHasStableIds(true);
        mRvCollect.setLayoutManager(new GridLayoutManager(mContext, 1));
        mRvCollect.setAdapter(fmCollectListAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        fmCollectListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.show("第" + position + "项", Toast.LENGTH_SHORT);
            }
        });

        fmCollectListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.bt_fm_cancel:
                        removeFm(mListFmStations.get(position));
                        break;
                    case R.id.bt_collect_compile:
                        ToastUtils.show("修改" + position);
                        updateFm(mListFmStations.get(position));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 删除FM收藏
     *
     * @param bean
     */
    public void removeFm(FmStationBean bean) {
        LitePal.deleteAll(FmStationBean.class, "stationName = ?", bean.getStationName().trim());
        mListFmStations.remove(bean);
        fmCollectListAdapter.notifyDataSetChanged();
    }

    /**
     * 修改FM收藏
     *
     * @param bean
     */
    public void updateFm(FmStationBean bean) {
        createPopupWindow(mFmPopupWindowLayout, 0, 0, 0, 0, true);
    }

    /**
     * 创建PopupWindow
     *
     * @param view    布局文件
     * @param width   宽度
     * @param high    高度
     * @param offsetX X轴偏移量
     * @param offsetY Y轴偏移量
     * @param isFocus 点击外部是否关闭
     */
    public void createPopupWindow(View view, int width, int high, int offsetX, int offsetY, boolean isFocus) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        Log.e("TAG", "widthPixels = " + widthPixels + ",heightPixels = " + heightPixels);
        mPopupWindow = new PopupWindow(view, (int) (widthPixels * 0.8), -2, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(isFocus);
        mPopupWindow.setFocusable(isFocus);
        //设置背景为半透明
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        //监听PopupWindow关闭时将透明度设置成原来
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        //设置弹窗位置PopupWindow的相关参数
        mPopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, offsetX, offsetY);
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
            case R.id.rb_confirm_update:
                closePopPopupWindow();
                break;
            case R.id.rb_cancel_update:
                closePopPopupWindow();
                break;
            default:
                break;
        }
    }
}
