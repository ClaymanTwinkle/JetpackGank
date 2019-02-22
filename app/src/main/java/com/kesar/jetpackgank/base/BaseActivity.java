package com.kesar.jetpackgank.base;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
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
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        initData();
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected <M extends ViewDataBinding> M getViewDataBinding() {
        return (M) mViewDataBinding;
    }

    protected void initData(){}
    protected void initView(){}
}
