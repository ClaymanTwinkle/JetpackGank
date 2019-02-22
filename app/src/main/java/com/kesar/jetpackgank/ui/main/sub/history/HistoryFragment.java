package com.kesar.jetpackgank.ui.main.sub.history;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kesar.jetpackgank.R;
import com.kesar.jetpackgank.databinding.HistoryFragmentBinding;
import com.kesar.jetpackgank.databinding.ListItemGankBinding;
import com.kesar.jetpackgank.data.Gank;
import com.kesar.jetpackgank.base.DataBindingViewHolder;
import com.kesar.jetpackgank.base.ViewModelFragment;
import com.kesar.jetpackgank.util.EmptyUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HistoryFragment extends ViewModelFragment<HistoryViewModel> {

    private HistoryAdapter mHistoryAdapter;

    private DividerItemDecoration mDividerItemDecoration;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.history_fragment;
    }

    @Override
    protected void initView() {
        super.initView();

        Activity activity = getActivity();
        if (activity == null) return;

        final HistoryFragmentBinding binding = getViewDataBinding();

        if(mDividerItemDecoration == null) {
            mDividerItemDecoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
            binding.listView.addItemDecoration(mDividerItemDecoration);
        }

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.refresh();
            }
        });

        mViewModel.getData().observe(this, new Observer<List<Gank>>() {
            @Override
            public void onChanged(List<Gank> ganks) {
                if(!EmptyUtils.isEmpty(ganks)) {
                    if(mHistoryAdapter==null) {
                        mHistoryAdapter = new HistoryAdapter();
                        mHistoryAdapter.setData(ganks);
                        binding.listView.setAdapter(mHistoryAdapter);
                    } else {
                        mHistoryAdapter.setData(ganks);
                        mHistoryAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        mViewModel.refresh();
    }

    private static class HistoryAdapter extends RecyclerView.Adapter<DataBindingViewHolder> {

        private List<Gank> dataList = new ArrayList<>();

        public void setData(List<Gank> dataList) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
        }

        @NonNull
        @Override
        public DataBindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ListItemGankBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_gank,parent, false);
            return new DataBindingViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull DataBindingViewHolder holder, int position) {
            ListItemGankBinding binding = holder.getBinding();
            binding.setData(dataList.get(position));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }
}
