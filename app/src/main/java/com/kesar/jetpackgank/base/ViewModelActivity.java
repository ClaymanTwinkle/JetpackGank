package com.kesar.jetpackgank.base;

import java.lang.reflect.ParameterizedType;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

/**
 * BaseActivity
 *
 * @author andy <br/>
 * create time: 2019/2/20 10:33
 */
public abstract class ViewModelActivity<T extends ViewModel> extends BaseActivity {

    protected T mViewModel;

    @Override
    protected void initData() {
        super.initData();
        Class<T> viewModelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mViewModel = ViewModelProviders.of(this).get(viewModelClass);
    }
}
