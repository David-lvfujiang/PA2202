package com.fenda.onn.ui.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fenda.onn.R;
import com.fenda.onn.bean.FmStationBean;
import com.fenda.onn.common.base.BaseFragment;
import com.fenda.onn.ui.adapter.FmCollectListAdapter;
import com.fenda.onn.ui.view.ClearWriteEditText;
import com.fenda.onn.utils.PopupWindowUtil;
import com.fenda.onn.utils.ToastUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/10
 * @Describe: FM收藏列表
 */
public class FmCollectListFragment extends BaseFragment {

    @BindView(R.id.rv_fm_collect)
    RecyclerView mRvCollect;
    static List<FmStationBean> mListFmStations = new ArrayList<FmStationBean>();
    private FmCollectListAdapter fmCollectListAdapter;
    private View mFmPopupWindowLayout;
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
        mBtConfirm.setOnClickListener(v -> {
            closePopPopupWindow();
        });
        mBtCancel.setOnClickListener(v -> {
            closePopPopupWindow();
        });
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
        fmCollectListAdapter.setOnItemClickListener((adapter, view, position) -> {
            ToastUtils.show("第" + position + "项", Toast.LENGTH_SHORT);
        });

        fmCollectListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
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
        PopupWindowUtil.createPopupWindow(getActivity(), mFmPopupWindowLayout, true, true,
                false, Gravity.CENTER);
    }

    /**
     * 关闭弹窗
     */
    public void closePopPopupWindow() {
        PopupWindowUtil.closePopPopupWindow();
    }

}
