package com.kesar.jetpackgank.ui.splash;

import android.os.Handler;
import android.text.TextUtils;

import com.kesar.jetpackgank.R;
import com.kesar.jetpackgank.databinding.SplashActivityBinding;
import com.kesar.jetpackgank.base.ViewModelActivity;
import com.kesar.jetpackgank.ui.main.MainActivity;

import androidx.lifecycle.Observer;

public class SplashActivity extends ViewModelActivity<SplashViewModel> {

    private final static long DELAY_MILLIS_NO_IMAGE = 500;
    private final static long DELAY_MILLIS_HAS_IMAGE = 2000;

    private Handler mHandler = new Handler();

    private Runnable mJumpMainActivityTask = new Runnable() {
        @Override
        public void run() {
            jumpMainActivity();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.splash_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        final SplashActivityBinding viewDataBinding = getViewDataBinding();
        mViewModel.getImageUrl().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (TextUtils.isEmpty(s)) {
                    delayToJump(DELAY_MILLIS_NO_IMAGE);
                } else {
                    viewDataBinding.setImageUrl(s);
                    delayToJump(DELAY_MILLIS_HAS_IMAGE);
                }
            }
        });
    }

    private void delayToJump(long delayMillis) {
        mHandler.postDelayed(mJumpMainActivityTask, delayMillis);
    }

    private void jumpMainActivity() {
        MainActivity.startActivity(this);
        finish();
    }
}
