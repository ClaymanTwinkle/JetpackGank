package com.kesar.jetpackgank.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

/**
 * ViewModelFragment
 *
 * @author andy <br/>
 * create time: 2019/2/20 19:16
 */
public abstract class ViewModelFragment<T extends ViewModel> extends BaseFragment {
    protected T mViewModel;

    private ViewDataBinding mViewDataBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);

        return mViewDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Class<T> viewModelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mViewModel = ViewModelProviders.of(this).get(viewModelClass);

        initView();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected <M extends ViewDataBinding> M getViewDataBinding() {
        return (M) mViewDataBinding;
    }

    protected void initView() {
    }
}
