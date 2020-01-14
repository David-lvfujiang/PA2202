package com.fenda.onn.ui.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fenda.onn.R;
import com.fenda.onn.bean.MusicEffectsBean;
import com.fenda.onn.common.base.BaseActivity;
import com.fenda.onn.ui.adapter.MusicEffectsAdapter;
import com.fenda.onn.ui.view.ShapeView;
import com.fenda.onn.utils.LogUtils;
import com.fenda.onn.utils.PopupWindowUtil;
import com.fenda.onn.utils.ThreaLocalUtil;
import com.fenda.onn.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author David-lvfujiang
 * @time 2019/12/30 17:18
 * desc  Light Dj 界面
 */
public class LightDjActivity extends BaseActivity implements ShapeView.AngleCallbackListener {

    private final int BASE_ANGLE = 45;
    @BindView(R.id.ivBack)
    ImageView mImgBack;
    @BindView(R.id.ivHelp)
    ImageView mImgHelp;
    @BindView(R.id.img_miusic_library)
    ImageView mImgMusicLibrary;
    @BindView(R.id.img_adjust_color)
    Button mBtAdjustColor;
    @BindView(R.id.bt_miusic_alarm)
    Button mBtMiusicAlarm;
    @BindView(R.id.bt_miusic_alien)
    Button mBtMiusicAlien;
    @BindView(R.id.bt_miusic_hit)
    Button mBtMiusicHit;
    @BindView(R.id.bt_miusic_kick)
    Button mBtMiusicKick;
    @BindView(R.id.bt_miusic_radio)
    Button mBtMiusicRadio;
    @BindView(R.id.bt_miusic_rewind)
    Button mBtMiusicRewind;
    @BindView(R.id.img_pic_palette)
    ShapeView mShapeView;
    @BindView(R.id.img_pic_disk)
    ShapeView mShapeViewDisk;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.drawer_right_recycle)
    RecyclerView mDrawerRightRecycle;
    private View mGuidePopupWindowLayout;
    private Button mBtCloseGuide;
    private List<MusicEffectsBean> musicEffectsBeanList = new ArrayList<MusicEffectsBean>();
    private Boolean isHideColorPicker = false;

    @Override
    public int onBindLayout() {
        return R.layout.activity_light_dj;
    }

    @Override
    public void initView() {
        mShapeView.setAngleCallbackListener(this);
        mShapeViewDisk.setAngleCallbackListener(this);
        mShapeViewDisk.setShowEffects(true);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        musicEffectsBeanList.add(new MusicEffectsBean(R.string.miusic_library_style_alarm, R.mipmap.pic__alarm));
        musicEffectsBeanList.add(new MusicEffectsBean(R.string.miusic_library_style_alien, R.mipmap.pic__alien));
        musicEffectsBeanList.add(new MusicEffectsBean(R.string.miusic_library_style_hit, R.mipmap.pic__hit));
        musicEffectsBeanList.add(new MusicEffectsBean(R.string.miusic_library_style_kick, R.mipmap.pic__alien));
        musicEffectsBeanList.add(new MusicEffectsBean(R.string.miusic_library_style_radio, R.mipmap.pic__hit));
        musicEffectsBeanList.add(new MusicEffectsBean(R.string.miusic_library_style_rewind, R.mipmap.pic__rewind));
        musicEffectsBeanList.add(new MusicEffectsBean(R.string.miusic_library_style_space, R.mipmap.pic__alarm));
        musicEffectsBeanList.add(new MusicEffectsBean(R.string.miusic_library_style_relax, R.mipmap.pic__alien));
        musicEffectsBeanList.add(new MusicEffectsBean(R.string.miusic_library_style_love, R.mipmap.pic__hit));
        //创建音效适配器
        MusicEffectsAdapter musicEffectsAdapter
                = new MusicEffectsAdapter(R.layout.item_music_effects, musicEffectsBeanList);
        mDrawerRightRecycle.setLayoutManager(new GridLayoutManager(mContext, 1));
        mDrawerRightRecycle.setAdapter(musicEffectsAdapter);
        //设置监听
        musicEffectsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ToastUtils.show(position + "");
        });
    }

    @OnClick({R.id.ivBack, R.id.ivHelp, R.id.img_miusic_library,
            R.id.bt_light_model, R.id.img_adjust_color, R.id.img_pic_click})
    public void handleClickEvent(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivHelp:
                mImgHelp.setVisibility(View.GONE);
                showGuidePopupWindow();
                break;
            case R.id.img_miusic_library:
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;
            case R.id.img_pic_click:
                ToastUtils.show("点击频闪");
                break;
            case R.id.bt_light_model:
                intent.setClass(mContext, LightModelActivity.class);
                startActivity(intent);
                break;
            case R.id.img_adjust_color:
                if (isHideColorPicker == false) {
                    mShapeViewDisk.setVisibility(View.VISIBLE);
                    mShapeView.setVisibility(View.GONE);
                    isHideColorPicker = true;
                } else {
                    mShapeViewDisk.setVisibility(View.GONE);
                    mShapeView.setVisibility(View.VISIBLE);
                    isHideColorPicker = false;
                }
                break;
            default:
                break;
        }
    }

    /**
     * 切换音效点击事件
     *
     * @param view
     */
    @OnClick({R.id.bt_miusic_alarm, R.id.bt_miusic_alien, R.id.bt_miusic_hit, R.id.bt_miusic_kick,
            R.id.bt_miusic_radio, R.id.bt_miusic_rewind})
    public void switchSoundEffects(View view) {
        switch (view.getId()) {
            case R.id.bt_miusic_alarm:
                ToastUtils.show("bt_miusic_alarm");
                break;
            case R.id.bt_miusic_alien:
                ToastUtils.show("bt_miusic_alien");
                break;
            case R.id.bt_miusic_hit:
                ToastUtils.show("bt_miusic_hit");
                break;
            case R.id.bt_miusic_kick:
                ToastUtils.show("bt_miusic_kick");
                break;
            case R.id.bt_miusic_radio:
                ToastUtils.show("bt_miusic_radio");
                break;
            case R.id.bt_miusic_rewind:
                ToastUtils.show("bt_miusic_rewind");
                break;
            default:
                finish();
        }
    }

    /**
     * 显示初始化指导说明界面
     */
    public void showGuidePopupWindow() {
        initGuidePopupWindow();
        PopupWindowUtil.createFullPopupWindow(LightDjActivity.this, mGuidePopupWindowLayout, true);
    }

    /**
     * 初始化指导说明界面
     */
    public void initGuidePopupWindow() {
        mGuidePopupWindowLayout = View.inflate(mContext, R.layout.layout_guide_dialog, null);
        mBtCloseGuide = mGuidePopupWindowLayout.findViewById(R.id.bt_guide_close);
        mBtCloseGuide.setOnClickListener(v -> {
            mImgHelp.setVisibility(View.VISIBLE);
            closePopPopupWindow();
        });
    }

    /**
     * 关闭弹窗
     */
    public void closePopPopupWindow() {
        PopupWindowUtil.closePopPopupWindow();
    }

    /**
     * 滑动圆盘回调接口
     *
     * @param view
     * @param angle
     */
    @Override
    public void getAngle(View view, int angle) {
        if (isHideColorPicker == false) {
            LogUtils.e("取色器：" + angle);
            mShapeViewDisk.setVisibility(View.VISIBLE);
            mShapeView.setVisibility(View.GONE);
            isHideColorPicker = true;
            setRouletteBackground(angle);
        } else {
            LogUtils.e("跑马灯：" + angle);
            //获取角度同步控制跑马灯
        }
    }

    /**
     * 切换圆盘颜色
     *
     * @param angle
     */
    public void setRouletteBackground(int angle) {
        //取色器显示时点击获取角度更新灯带颜色
        if (angle >= 0 && angle < BASE_ANGLE) {
            mShapeViewDisk.setBackgroundResource(R.mipmap.pic_cyan);
        } else if (angle >= BASE_ANGLE && angle < BASE_ANGLE * 2) {
            mShapeViewDisk.setBackgroundResource(R.mipmap.pic_green);
        } else if (angle >= BASE_ANGLE * 2 && angle < BASE_ANGLE * 3) {
            mShapeViewDisk.setBackgroundResource(R.mipmap.pic_poolblue);
        } else if (angle >= BASE_ANGLE * 3 && angle < BASE_ANGLE * 4) {
            mShapeViewDisk.setBackgroundResource(R.mipmap.pic_orange);
        } else if (angle >= BASE_ANGLE * 4 && angle < BASE_ANGLE * 5) {
            mShapeViewDisk.setBackgroundResource(R.mipmap.pic_red);
        } else if (angle >= BASE_ANGLE * 5 && angle < BASE_ANGLE * 6) {
            mShapeViewDisk.setBackgroundResource(R.mipmap.pic_pink);
        } else if (angle >= BASE_ANGLE * 6 && angle < BASE_ANGLE * 7) {
            mShapeViewDisk.setBackgroundResource(R.mipmap.pic_purple);
        } else if (angle >= BASE_ANGLE * 7 && angle < BASE_ANGLE * 8) {
            mShapeViewDisk.setBackgroundResource(R.mipmap.pic_blue);
        }
    }
}
