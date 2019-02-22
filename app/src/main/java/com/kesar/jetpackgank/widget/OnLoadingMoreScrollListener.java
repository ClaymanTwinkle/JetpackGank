package com.kesar.jetpackgank.widget;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * OnLoadingMoreScrollListener
 *
 * @author andy <br/>
 * create time: 2019/2/21 19:08
 */
public abstract class OnLoadingMoreScrollListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                int adapterItemCount = recyclerView.getAdapter() == null ? 0 : recyclerView.getAdapter().getItemCount();
                if (firstCompletelyVisibleItemPosition > 0 && lastVisibleItemPosition >= adapterItemCount - 1) {
                    onLoadingMore();
                }
            }
        }
    }

    protected abstract void onLoadingMore();
}
