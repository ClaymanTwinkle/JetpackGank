package com.kesar.jetpackgank.ui.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.kesar.jetpackgank.R;
import com.kesar.jetpackgank.base.BaseActivity;
import com.kesar.jetpackgank.databinding.WebActivityBinding;

import androidx.fragment.app.Fragment;

public class WebActivity extends BaseActivity {

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
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_URL, url);

        return intent;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.web_activity;
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

        WebActivityBinding binding = getViewDataBinding();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(mTitle);

        WebView webView = binding.webView;
        final ProgressBar progressBar = binding.progressBar;

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBar.setProgress(newProgress);
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl(mUrl);
    }
}
