package com.shangtao.base.binding.viewadapter.image;


import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.shangtao.base.model.glide.GlideUtil;

public final class ViewAdapter {

    @BindingAdapter(value = {"url","placeholderRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url,int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            GlideUtil.loadImage(imageView,url,placeholderRes);
        }
    }

}

