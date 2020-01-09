package com.fenda.onn.ui.activity;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fenda.onn.R;
import com.fenda.onn.common.base.BaseActivity;
import com.fenda.onn.ui.fragment.FmCollectListFragment;
import com.fenda.onn.ui.fragment.FmSearchListFragment;
import com.fenda.onn.ui.view.RulerView;
import com.fenda.onn.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * @author David-lvfujiang
 * @time 2019/12/30 17:18
 * desc  FM界面
 */
public class FmRadioActivity extends BaseActivity implements RulerView.FmRateUpdateListener {
    private final int MULTIPLE = 1000;
    private int newFmRate = 0;
    @BindView(R.id.tv_fm_rate)
    TextView mTvFmRate;
    @BindView(R.id.fm_ruler_view)
    RulerView mRulerView;
    @BindView(R.id.bt_fm_icon_next)
    Button mBtNext;
    @BindView(R.id.bt_fm_icon_prev)
    Button mBtPrev;
    @BindView(R.id.rb_search)
    RadioButton mRbSearch;
    @BindView(R.id.rb_collct)
    RadioButton mRbCollect;
    @BindView(R.id.fl_Content)
    FrameLayout mFrameLayout;
    FmSearchListFragment mFmSearchListFragment;
    FmCollectListFragment mFmCollectListFragment;

    @Override
    public int onBindLayout() {
        return R.layout.activity_fm_radio;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        //刻度尺滑动监听
        mRulerView.setFmRateUpdateListener(this);
        mFmSearchListFragment = (FmSearchListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fm_search_list_ragment);
        mFmCollectListFragment = (FmCollectListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fm_collect_list_ragment);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.bt_fm_icon_next, R.id.bt_fm_icon_prev, R.id.ivBack})
    public void adjustFm(View v) {
        switch (v.getId()) {
            case R.id.bt_fm_icon_next:
                //获取微调后的数值（旧值+微调频率）
                newFmRate = (int) ((Float.valueOf(mRulerView.getWeightStr().trim()) + 0.1) * MULTIPLE);
                Log.e("bodyweight", newFmRate + "");
                //调整刻度尺
                mRulerView.setBodyWeight(newFmRate);
                //播放对应的FM
                playFm(String.valueOf(Float.valueOf(mRulerView.getWeightStr().trim()) + 0.1));
                break;
            case R.id.bt_fm_icon_prev:
                newFmRate = (int) ((Float.valueOf(mRulerView.getWeightStr().trim()) - 0.1) * MULTIPLE);
                Log.e("bodyweight", newFmRate + "");
                mRulerView.setBodyWeight(newFmRate);
                playFm(String.valueOf(Float.valueOf(mRulerView.getWeightStr().trim()) + -0.1));
                break;
            case R.id.ivBack:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 滑动刻度尺回调接口
     *
     * @param rate 滑动停止的FM频率
     */
    @Override
    public void callbackFmRate(String rate) {
        Log.e("result", rate + "");
        //更新显示的FM
        mTvFmRate.setText(rate);
        playFm(rate);
    }

    /**
     * 播放FM
     *
     * @param rate
     */
    public void playFm(String rate) {
    }

    /**
     * 根据RadioButton切换收藏列表和搜索列表
     *
     * @param view
     * @param ischanged
     */
    @OnCheckedChanged({R.id.rb_collct, R.id.rb_search})
    public void onRadioCheck(CompoundButton view, boolean ischanged) {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.rb_collct:
                if (ischanged) {
                    Toast.makeText(mContext, "收藏列表", Toast.LENGTH_SHORT).show();
                    mRbSearch.setAlpha(0.5F);
                    mRbCollect.setAlpha(1.0f);
                    ft.hide(mFmSearchListFragment);
                    ft.show(mFmCollectListFragment);
                }
                break;
            case R.id.rb_search:
                if (ischanged) {
                    Toast.makeText(mContext, "搜索列表", Toast.LENGTH_SHORT).show();
                    mRbCollect.setAlpha(0.5F);
                    mRbSearch.setAlpha(1.0f);
                    ft.hide(mFmCollectListFragment);
                    ft.show(mFmSearchListFragment);
                }
                break;
            default:
                break;
        }
        ft.commit();
    }
}
