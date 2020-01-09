package com.fenda.onn.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.fenda.onn.R;
import com.fenda.onn.common.base.BaseActivity;
import com.fenda.onn.common.base.BaseFragment;
import com.fenda.onn.ui.adapter.ProductionSeriesPagerAdapter;
import com.fenda.onn.ui.fragment.EarPhoneFragment;
import com.fenda.onn.ui.fragment.SoundFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;

/**
 * @author kevin.wangzhiqiang
 * @time 2019/12/30 15:51
 * desc  产品系列界面
 */
public class ProductionSeriesActivity extends BaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rbSound)
    RadioButton rbSound;
    @BindView(R.id.rbEarphone)
    RadioButton rbEarphone;
    @BindView(R.id.rgMenu)
    RadioGroup rgMenu;
    @BindView(R.id.vpContent)
    ViewPager vpContent;
    private List<BaseFragment> mFragmentList = new ArrayList<>();

    @Override
    public int onBindLayout() {
        return R.layout.activity_production_series;
    }

    @Override
    public void initView() {
    }

    @Override
    public boolean isToolbarImmersion() {
        return true;
    }

    @Override
    protected void initData() {
        super.initData();
        SoundFragment soundSeriesFragment = new SoundFragment();
        EarPhoneFragment earPhoneFragment = new EarPhoneFragment();
        mFragmentList.add(soundSeriesFragment);
        mFragmentList.add(earPhoneFragment);
        FragmentManager manger = getSupportFragmentManager();
        vpContent.setAdapter(new ProductionSeriesPagerAdapter(manger, mFragmentList));
    }

    /**
     * 产品系列选择事件
     *
     * @param view
     * @param ischanged
     **/
    @OnCheckedChanged({R.id.rbSound, R.id.rbEarphone})
    public void onRadioCheck(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.rbSound:
                if (ischanged) {
                    vpContent.setCurrentItem(0);
                }
                break;
            case R.id.rbEarphone:
                if (ischanged) {
                    vpContent.setCurrentItem(1);
                }
                break;
            default:
                break;
        }
    }
}
