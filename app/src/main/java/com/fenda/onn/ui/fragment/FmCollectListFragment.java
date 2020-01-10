package com.fenda.onn.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fenda.onn.R;
import com.fenda.onn.bean.FmStationBean;
import com.fenda.onn.common.base.BaseFragment;
import com.fenda.onn.ui.adapter.FmCollectListAdapter;
import com.fenda.onn.utils.LogUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/6
 * @Describe: FM收藏列表
 */
public class FmCollectListFragment extends BaseFragment {
    @BindView(R.id.rv_fm_collect)
    RecyclerView mRvCollect;
    FmCollectListAdapter mCollectListAdapter;
    static ArrayList<FmStationBean> mListFmStations = new ArrayList<FmStationBean>();
    ArrayList<String> mListFmStationName = new ArrayList<String>();
    HashSet<String> mCollectSets = new HashSet<String>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public int onBindLayout() {
        return R.layout.fragment_fm_collect;
    }

    @Override
    protected void initView() {
    }


    @Override
    protected void initData() {
        super.initData();
        getFmStationSet();
    }

//    @Override
//    public void setUserVisibleHint(boolean b) {
//        if (mRvCollect != null) {
//            Log.e("TAG", "setUserVisibleHint");
//            getFmStationSet();
//            mCollectListAdapter.loadMoreComplete();
//        }
//    }

    /**
     * 获取FM收藏列表
     */
    public void getFmStationSet() {
        mListFmStations = (ArrayList<FmStationBean>) LitePal.findAll(FmStationBean.class);
        LogUtils.e(mListFmStations.size() + "数组");
        mCollectListAdapter = new FmCollectListAdapter(R.layout.item_fm_collect, mListFmStations);
        mCollectListAdapter.openLoadAnimation();
        mRvCollect.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvCollect.setAdapter(mCollectListAdapter);
        mCollectListAdapter.notifyDataSetChanged();

    }

    /**
     * 删除FM收藏item
     *
     * @param bean
     */
    public void updateFmData(FmStationBean bean) {
        LitePal.deleteAll(FmStationBean.class, "stationName = ?", bean.getStationName().trim());
        mListFmStations.remove(bean);
        mCollectListAdapter.notifyDataSetChanged();
    }

    @Override
    public void initListener() {
        mCollectListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(mContext, "第" + position + "项", Toast.LENGTH_SHORT).show();
            }
        });
        mCollectListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.bt_fm_cancel:
                        Toast.makeText(mContext, "取消收藏" + position, Toast.LENGTH_SHORT).show();
                        LogUtils.e(mListFmStations.size() + "数组");
                        LogUtils.e(mListFmStations.get(position).getStationName());
                        updateFmData(mListFmStations.get(position));
                        break;
                    case R.id.bt_collect_compile:
                        Toast.makeText(mContext, "编辑" + position, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

    }
}
