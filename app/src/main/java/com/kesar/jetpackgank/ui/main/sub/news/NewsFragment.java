package com.kesar.jetpackgank.ui.main.sub.news;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kesar.jetpackgank.R;
import com.kesar.jetpackgank.base.BaseRecyclerViewAdapter;
import com.kesar.jetpackgank.base.DataBindingViewHolder;
import com.kesar.jetpackgank.base.ViewModelFragment;
import com.kesar.jetpackgank.databinding.ListItemContentGankListBinding;
import com.kesar.jetpackgank.databinding.ListItemTitleGankListBinding;
import com.kesar.jetpackgank.databinding.NewsFragmentBinding;
import com.kesar.jetpackgank.data.Gank;
import com.kesar.jetpackgank.ui.web.WebActivity;
import com.kesar.jetpackgank.util.EmptyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.kesar.jetpackgank.Constant.WELFARE_TYPE;

public class NewsFragment extends ViewModelFragment<NewsViewModel> {

    private NewsFragmentBinding mBinding;
    private NewsAdapter mAdapter;

    private DividerItemDecoration mDividerItemDecoration;

    private Observer<HashMap<String, List<Gank>>> mObserver = new Observer<HashMap<String, List<Gank>>>() {
        @Override
        public void onChanged(HashMap<String, List<Gank>> stringListHashMap) {
            mBinding.swipeRefreshLayout.setRefreshing(false);
            updateData(stringListHashMap);
        }
    };

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.news_fragment;
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
        if(mAdapter == null) {
            mAdapter = new NewsAdapter();
            mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Object data = mAdapter.getData(position);
                    if(data instanceof Gank) {
                        Gank gank = (Gank) data;
                        mViewModel.saveHistory(gank);
                        WebActivity.startActivity(NewsFragment.this, gank.getDesc(), gank.getUrl());
                    }
                }
            });
        }
        mBinding.listView.setAdapter(mAdapter);

        LiveData<HashMap<String, List<Gank>>> data = mViewModel.getDataList();
        data.removeObserver(mObserver);
        data.observe(this, mObserver);

        mViewModel.refresh();
    }

    private void updateData(HashMap<String, List<Gank>> stringListHashMap) {
        if (!EmptyUtils.isEmpty(stringListHashMap)) {
            List<Gank> welfareList = stringListHashMap.remove(WELFARE_TYPE);

            if (!EmptyUtils.isEmpty(welfareList)) {
                Gank welfare = welfareList.get(0);
                mBinding.setHeaderPic(welfare.getUrl());
            }

            mAdapter.setData(stringListHashMap);
            mAdapter.notifyDataSetChanged();
        }
    }

    private static class NewsAdapter extends BaseRecyclerViewAdapter<DataBindingViewHolder> {

        private final static int VIEW_TYPE_TITLE = 0;
        private final static int VIEW_TYPE_CONTENT = 1;

        private List<Object> dataList = new ArrayList<>();

        public void setData(HashMap<String, List<Gank>> map) {
            dataList.clear();
            for (String key : map.keySet()) {
                dataList.add(key);
                List<Gank> list = map.get(key);
                if (list != null) {
                    dataList.addAll(list);
                }
            }
        }

        public Object getData(int position) {
            return dataList.get(position);
        }

        @Override
        public int getItemViewType(int position) {
            Object o = dataList.get(position);
            if (o instanceof Gank) {
                return VIEW_TYPE_CONTENT;
            } else {
                return VIEW_TYPE_TITLE;
            }
        }

        @NonNull
        @Override
        protected DataBindingViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
            int layoutId;
            if (viewType == VIEW_TYPE_TITLE) {
                layoutId = R.layout.list_item_title_gank_list;
            } else {
                layoutId = R.layout.list_item_content_gank_list;
            }
            return new DataBindingViewHolder(DataBindingUtil.inflate(inflater, layoutId, parent, false));
        }

        @Override
        protected void onBindViewHolder(int position, @NonNull DataBindingViewHolder holder) {
            int viewType = getItemViewType(position);
            switch (viewType) {
                case VIEW_TYPE_TITLE: {
                    String title = (String) dataList.get(position);
                    ListItemTitleGankListBinding binding = holder.getBinding();
                    binding.setTitle(title);
                }
                break;
                case VIEW_TYPE_CONTENT: {
                    Gank gank = (Gank) dataList.get(position);
                    ListItemContentGankListBinding binding = holder.getBinding();
                    binding.setGank(gank);
                }
                break;
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }
}
