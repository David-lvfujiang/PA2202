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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

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
    ArrayList<FmStationBean> mListFmStations = new ArrayList<FmStationBean>();
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
        mCollectListAdapter = new FmCollectListAdapter(R.layout.item_fm_collect, mListFmStations);
        mCollectListAdapter.openLoadAnimation();
        mRvCollect.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvCollect.setAdapter(mCollectListAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e("TAG", "setUserVisibleHint");
        getFmStationSet();
        mCollectListAdapter.notifyDataSetChanged();
    }

    /**
     * 获取FM收藏列表
     */
    public void getFmStationSet() {
    }

    /**
     * 删除FM收藏item
     *
     * @param set
     */
    public void updateFmData(HashSet<String> set) {

    }

    @Override
    protected void initListener() {
        super.initListener();
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
                        mListFmStations.remove(position);
                        mListFmStationName.remove(position);
                        HashSet<String> hashSet = new HashSet<String>(mListFmStationName);
                        updateFmData(hashSet);
                        mCollectListAdapter.notifyDataSetChanged();
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
