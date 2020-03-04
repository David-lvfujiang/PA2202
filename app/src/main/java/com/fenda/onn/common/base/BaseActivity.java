package com.fenda.onn.common.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.fenda.onn.AppApplication;
import com.fenda.onn.R;
import com.fenda.onn.common.view.LoadingInitView;
import com.fenda.onn.common.view.NetErrorView;
import com.fenda.onn.common.view.NoDataView;
import com.fenda.onn.utils.AppManager;
import com.fenda.onn.utils.NetUtil;
import com.fenda.onn.utils.StatusBarUtil;
import org.greenrobot.eventbus.EventBus;

/**
 * @author David-lvfujiang
 * @time 2019/12/26 15:13
 * desc 基类Activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected NetErrorView mNetErrorView;
    protected NoDataView mNoDataView;
    protected LoadingInitView mLoadingInitView;
    private ViewStub mViewContent;
    private ViewStub mViewInitLoading;
    private ViewStub mViewNoData;
    private ViewStub mViewError;
    private boolean isConfigChange = false;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        boolean isFull = initStatusBar();
        setContentView(R.layout.common_activity_root);
        AppApplication.addActivity(this);
        if (!isFull) {
            if (isToolbarImmersion()) {
                StatusBarUtil.setFitsSystemWindows(this, true);
            }
            StatusBarUtil.setTransparent(this);
        } else {
            NoTitleFullScreen();
        }
        mContext = this;
        //        ScreenFitUtil.setCustomDensity(this, (Application) AppApplication.getContext());
        initCommonView();
        //初始化butterknife
        mUnBinder = ButterKnife.bind(this);
        init();
        initView();
        initData();
        initListener();
        AppManager.getAppManager().addActivity(this);
    }

    public boolean initStatusBar() {
        return false;
    }

    // 是否是Toolbar标题栏沉浸式
    public boolean isToolbarImmersion() {
        return false;
    }

    /**
     * 关闭页面，如果需要在关闭页面时做特殊处理
     * 请重写这个方法
     */
    protected void finishActivity() {
        this.finish();
    }

    /**
     * 全屏显示 设置这个状态可能无法下拉状态栏
     */
    private void NoTitleFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected void initCommonView() {
        mViewContent = findViewById(R.id.view_stub_content);
        mViewInitLoading = findViewById(R.id.view_stub_init_loading);
        mViewError = findViewById(R.id.view_stub_error);
        mViewNoData = findViewById(R.id.view_stub_nodata);
        mViewContent.setLayoutResource(onBindLayout());
        mViewContent.inflate();
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
            int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public abstract int onBindLayout();

    public abstract void initView();

    protected void init() {
    }

    protected void initData() {

    }

    protected void initListener() {
    }

    public void showInitLoadView() {
        showInitLoadView(true);
    }

    public void hideInitLoadView() {
        showInitLoadView(false);
    }

    public void showNoDataView() {
        showNoDataView(true);
    }

    public void showNoDataView(int resid) {
        showNoDataView(true, resid);
    }

    public void hideNoDataView() {
        showNoDataView(false);
    }

    public void hideNetWorkErrView() {
                showNetWorkErrView(false);
    }

    public void showNetWorkErrView() {
                showNetWorkErrView(true);
    }

    private void showInitLoadView(boolean show) {
        if (mLoadingInitView == null) {
            //inflate只能调用一次
            View view = mViewInitLoading.inflate();
            mLoadingInitView = view.findViewById(R.id.view_init_loading);
        }
        mLoadingInitView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showNetWorkErrView(boolean show) {
        if (mNetErrorView == null) {
            View view = mViewError.inflate();
            mNetErrorView = view.findViewById(R.id.view_net_error);
            mNetErrorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!NetUtil.checkNetToast()) {
                        return;
                    }
                    hideNetWorkErrView();
                    initData();
                }
            });
        }
        mNetErrorView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showNoDataView(boolean show) {
        if (mNoDataView == null) {
            View view = mViewNoData.inflate();
            mNoDataView = view.findViewById(R.id.view_no_data);
        }
        mNoDataView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showContentView(boolean show) {
        if (mViewContent != null) {
            mViewContent.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    private void showNoDataView(boolean show, int resid) {
        showNoDataView(show);
        if (show) {
            mNoDataView.setNoDataView(resid);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //横竖切换
        isConfigChange = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppApplication.removeActivity(this);
        if (!isConfigChange) {
            AppManager.getAppManager().finishActivity(this);
        }
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        EventBus.getDefault().unregister(this);
    }
}
