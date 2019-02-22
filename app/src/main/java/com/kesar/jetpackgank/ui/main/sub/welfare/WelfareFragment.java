package com.kesar.jetpackgank.ui.main.sub.welfare;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kesar.jetpackgank.R;
import com.kesar.jetpackgank.base.BaseRecyclerViewAdapter;
import com.kesar.jetpackgank.base.DataBindingViewHolder;
import com.kesar.jetpackgank.base.ViewModelFragment;
import com.kesar.jetpackgank.databinding.ListItemMeiziBinding;
import com.kesar.jetpackgank.databinding.WelfareFragmentBinding;
import com.kesar.jetpackgank.data.Gank;
import com.kesar.jetpackgank.ui.picture.PictureActivity;
import com.kesar.jetpackgank.util.EmptyUtils;
import com.kesar.jetpackgank.widget.OnLoadingMoreScrollListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class WelfareFragment extends ViewModelFragment<WelfareViewModel> {

    private WelfareAdapter mWelfareAdapter;
    private WelfareFragmentBinding mBinding;

    private DividerItemDecoration mDividerItemDecoration;

    private Observer<List<Gank>> mObserver = new Observer<List<Gank>>() {
        @Override
        public void onChanged(List<Gank> ganks) {
            mBinding.swipeRefreshLayout.setRefreshing(false);
            updateData(ganks);
        }
    };

    private OnLoadingMoreScrollListener mOnLoadingMoreScrollListener = new OnLoadingMoreScrollListener() {
        @Override
        protected void onLoadingMore() {
            mViewModel.loadMore();
        }
    };

    public static WelfareFragment newInstance() {
        return new WelfareFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.welfare_fragment;
    }

    @Override
    protected void initView() {
        super.initView();

        Activity activity = getActivity();
        if (activity == null) return;

        mBinding = getViewDataBinding();

        mBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.refresh();
            }
        });

        if(mDividerItemDecoration == null) {
            mDividerItemDecoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
            mBinding.listView.addItemDecoration(mDividerItemDecoration);
        }

        mBinding.listView.removeOnScrollListener(mOnLoadingMoreScrollListener);
        mBinding.listView.addOnScrollListener(mOnLoadingMoreScrollListener);

        LiveData<List<Gank>> data = mViewModel.getData();

        data.removeObserver(mObserver);
        data.observe(this, mObserver);

        mViewModel.refresh();
    }

    private void updateData(List<Gank> ganks) {
        if(!EmptyUtils.isEmpty(ganks)) {
            if(mWelfareAdapter == null) {
                mWelfareAdapter = new WelfareAdapter();
                mWelfareAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Gank gank = mWelfareAdapter.getData(position);
                        PictureActivity.startActivity(WelfareFragment.this, gank.getDesc(), gank.getUrl());
                    }
                });
                mWelfareAdapter.setData(ganks);
                mBinding.listView.setAdapter(mWelfareAdapter);
            } else{
                mWelfareAdapter.setData(ganks);
                mWelfareAdapter.notifyDataSetChanged();
            }
        }
    }

    private static class WelfareAdapter extends BaseRecyclerViewAdapter<DataBindingViewHolder> {

        private List<Gank> dataList = new ArrayList<>();

        public void setData(List<Gank> dataList) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
        }

        public Gank getData(int position) {
            return dataList.get(position);
        }

        @NonNull
        @Override
        protected DataBindingViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
            ListItemMeiziBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_meizi,parent, false);
            return new DataBindingViewHolder(binding);
        }

        @Override
        protected void onBindViewHolder(int position, @NonNull DataBindingViewHolder holder) {
            ListItemMeiziBinding binding = holder.getBinding();
            binding.setPicUrl(dataList.get(position).getUrl());
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }
}
