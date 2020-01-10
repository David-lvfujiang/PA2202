package com.fenda.onn.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.fenda.onn.common.base.BaseFragment;

import java.util.List;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/9
 * @Describe:
 */
public class FmFragmentAdapter  extends FragmentStatePagerAdapter {
    private List<BaseFragment> list;

    public FmFragmentAdapter(FragmentManager fm, List<BaseFragment> list) {
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
