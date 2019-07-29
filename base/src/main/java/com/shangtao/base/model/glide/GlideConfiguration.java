package com.shangtao.base.model.glide;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.shangtao.base.model.file.FileCacheUtils;

import java.io.InputStream;

/**
 * Glide配置信息
 */
public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 设置缓存路径和允许缓存的最大大小
        builder.setDiskCache(new DiskLruCacheFactory(FileCacheUtils.getImageDir(context).getAbsolutePath(), 20 * 1024 * 1024));
        // 设置图片为ARGB_888888
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // 将Glide的内置引擎替换为OkHttp,与Retrofit一致，方便处理
        glide.register(GlideUrl.class, InputStream.class, new OkHttpModeLoader.Factory());
    }

}
