package com.kesar.jetpackgank.base;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * DataBindingViewHolder
 *
 * @author andy <br/>
 * create time: 2019/2/21 15:02
 */
public class DataBindingViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public DataBindingViewHolder(@NonNull ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding =binding;
    }

    public <T extends ViewDataBinding> T getBinding() {
        return (T) binding;
    }
}
