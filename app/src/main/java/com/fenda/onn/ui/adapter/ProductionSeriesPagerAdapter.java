package com.fenda.onn.ui.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.fenda.onn.common.base.BaseFragment;

import java.util.List;

public class ProductionSeriesPagerAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> list;

    public ProductionSeriesPagerAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
