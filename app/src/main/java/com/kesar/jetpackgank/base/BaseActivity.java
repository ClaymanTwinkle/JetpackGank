package com.kesar.jetpackgank.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * BaseActivity
 *
 * @author andy <br/>
 * create time: 2019/2/20 16:03
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ViewDataBinding mViewDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        initData();
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected <M extends ViewDataBinding> M getViewDataBinding() {
        return (M) mViewDataBinding;
    }

    public void initContentView() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    protected void initData() {
    }

    protected void initView() {
    }


    public void setFullScreen(boolean isShowStatusBar, boolean isShowNavigationBar) {
        View decorView = getWindow().getDecorView();
        int uiOptions = decorView.getSystemUiVisibility();

        /* （上）状态栏相关 */
        if (!isShowStatusBar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                uiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN; // 隐藏状态栏
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                uiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN; // 布局全屏，如果有状态栏就显示在状态栏下面
            }
        }

        /* （下）导航栏相关 */

        if (!isShowNavigationBar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                uiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION; // 布局全屏，如果有导航栏就显示在导航栏下面
            }

            uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;  // 隐藏导航栏
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            uiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;  // 隐藏状态栏或者导航栏时布局不变化，该留空的就留空
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;  // 透明化的状态栏或者导航栏？
        }

        decorView.setSystemUiVisibility(uiOptions);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setNavigationStatusColor(Color.TRANSPARENT);
    }

    public void setNavigationStatusColor(int color) {
        //VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setNavigationBarColor(color);
            getWindow().setStatusBarColor(color);
        }
    }
}
