package com.kesar.jetpackgank;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kesar.jetpackgank.util.EmptyUtils;

import androidx.databinding.BindingAdapter;

/**
 * ImageViewAttrAdapter
 *
 * @author andy <br/>
 * create time: 2019/2/20 11:09
 */
public class ImageViewAttrAdapter {
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(EmptyUtils.ifNull(url, ""))
                .into(imageView);
    }
}
