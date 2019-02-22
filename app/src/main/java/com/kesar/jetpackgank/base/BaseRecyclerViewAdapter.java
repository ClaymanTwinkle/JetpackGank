package com.kesar.jetpackgank.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * BaseRecyclerViewAdapter
 *
 * @author andy <br/>
 * create time: 2019/2/22 09:46
 */
public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolderWrapper<VH>> {

    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public final ViewHolderWrapper<VH> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ViewHolderWrapper<>(onCreateViewHolder(inflater, parent, viewType), onItemClickListener);
    }

    @Override
    public final void onBindViewHolder(@NonNull ViewHolderWrapper<VH> holder, int position) {
        holder.position = position;
        onBindViewHolder(position, holder.viewHolder);
    }

    @NonNull
    protected abstract VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType);

    protected abstract void onBindViewHolder(int position, @NonNull VH holder);

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class ViewHolderWrapper<T extends RecyclerView.ViewHolder> extends RecyclerView.ViewHolder implements View.OnClickListener {
        final T viewHolder;
        int position;
        OnItemClickListener onItemClickListener;

        ViewHolderWrapper(@NonNull T viewHolder, OnItemClickListener onItemClickListener) {
            super(viewHolder.itemView);
            this.viewHolder = viewHolder;
            this.onItemClickListener = onItemClickListener;
            this.viewHolder.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, position);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
