package com.fenda.onn.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fenda.onn.R;
import com.fenda.onn.bean.FmStationBean;
import com.fenda.onn.common.base.BaseFragment;
import com.fenda.onn.ui.adapter.FmSearchListAdapter;
import com.fenda.onn.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/6
 * @Describe: FM搜索列表
 */
public class FmSearchListFragment extends BaseFragment {
    @BindView(R.id.rv_fm_search)
    RecyclerView mRvSearch;
    @BindView(R.id.sr_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    FmSearchListAdapter mSearchListAdapter;
    List<FmStationBean> mListFmStations = new ArrayList<FmStationBean>();
    HashSet<String> mCollectSets = new HashSet<String>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public int onBindLayout() {
        return R.layout.fragment_fm_search;
    }

    @Override
    protected void initView() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        super.initData();
        handleData();
        mSearchListAdapter = new FmSearchListAdapter(R.layout.item_fm_search, mListFmStations);
        mSearchListAdapter.openLoadAnimation();
        mSearchListAdapter.setHasStableIds(true);
        mRvSearch.setLayoutManager(new GridLayoutManager(mContext, 1));
        mRvSearch.setAdapter(mSearchListAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e("TAG", "setUserVisibleHint");
        mSearchListAdapter.notifyDataSetChanged();
    }

    public void handleData() {
        //测试数据
        for (int i = 0; i < 100; i++) {
            mListFmStations.add(new FmStationBean(String.valueOf(i)));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initListener() {
        super.initListener();
        mSearchListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.e("加载", "哈哈哈");
                handleData();
                mSearchListAdapter.loadMoreComplete();
                mSearchListAdapter.notifyDataSetChanged();
            }
        }, mRvSearch);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.fd_text_3c88d4, null));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //数据重新加载完成后，更新适配器
                mSearchListAdapter.notifyDataSetChanged();
                //取消刷新
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mSearchListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(mContext, "第" + position + "项", Toast.LENGTH_SHORT).show();
            }
        });
        mSearchListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.bt_compile:
                        Toast.makeText(mContext, "编辑" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_fm_love:
                        Toast.makeText(mContext, "收藏" + position, Toast.LENGTH_SHORT).show();
                        String stationName = mListFmStations.get(position).getStationName();
                        FmStationBean bean = new FmStationBean(stationName);
                        boolean success = bean.save();
                        LogUtils.e(success+"成功");
                        Toast.makeText(mContext, stationName + position, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
