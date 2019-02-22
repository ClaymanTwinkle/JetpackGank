package com.kesar.jetpackgank.ui.main;

import android.content.Context;
import android.content.Intent;

import com.kesar.jetpackgank.R;
import com.kesar.jetpackgank.base.BaseActivity;
import com.kesar.jetpackgank.databinding.MainActivityBinding;
import com.kesar.jetpackgank.ui.main.sub.history.HistoryFragment;
import com.kesar.jetpackgank.ui.main.sub.news.NewsFragment;
import com.kesar.jetpackgank.ui.main.sub.welfare.WelfareFragment;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainActivity extends BaseActivity {

    private final static String[] TAB_TITLES = {"最新", "福利", "历史"};

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        final MainActivityBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        viewDataBinding.mViewPager.setOffscreenPageLimit(2);
        viewDataBinding.mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            private Fragment[] fragments = new Fragment[TAB_TITLES.length];

            @NonNull
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = fragments[position];
                if (fragment == null) {
                    switch (position) {
                        case 0:
                            fragment = NewsFragment.newInstance();
                            break;
                        case 1:
                            fragment = WelfareFragment.newInstance();
                            break;
                        case 2:
                            fragment = HistoryFragment.newInstance();
                            break;
                        default:
                            fragment = new Fragment();
                            break;
                    }
                    fragments[position] = fragment;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return TAB_TITLES.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return TAB_TITLES[position];
            }
        });

        viewDataBinding.mTabLayout.setupWithViewPager(viewDataBinding.mViewPager);
    }
}
