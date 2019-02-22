package com.kesar.jetpackgank.ui.picture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.kesar.jetpackgank.R;
import com.kesar.jetpackgank.base.BaseActivity;
import com.kesar.jetpackgank.databinding.PictureActivityBinding;

import androidx.fragment.app.Fragment;

public class PictureActivity extends BaseActivity {

    private static final String EXTRA_URL = "url";
    private static final String EXTRA_TITLE = "title";

    private String mUrl = "";
    private String mTitle = "";

    public static void startActivity(Activity activity, String title, String url) {
        activity.startActivity(buildIntent(activity, title, url));
    }

    public static void startActivity(Fragment fragment, String title, String url) {
        fragment.startActivity(buildIntent(fragment.getContext(), title, url));
    }

    private static Intent buildIntent(Context context, String title, String url) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_URL, url);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.picture_activity;
    }

    @Override
    protected void initData() {
        super.initData();
        mUrl = getIntent().getStringExtra(EXTRA_URL);
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);
    }

    @Override
    protected void initView() {
        super.initView();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(mTitle);

        PictureActivityBinding binding = getViewDataBinding();
        binding.setUrl(mUrl);
    }
}
