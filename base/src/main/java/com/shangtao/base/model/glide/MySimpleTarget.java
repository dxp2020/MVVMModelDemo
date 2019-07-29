package com.shangtao.base.model.glide;


import android.graphics.drawable.Drawable;


public interface MySimpleTarget<T> {
    /**
     * Callback when an image has been successfully loaded.
     * <p>
     * <strong>Note:</strong> You must not recycle the bitmap.
     * @param bitmap  内容
     */
    void onResourceReady(T bitmap);

    /**
     * Callback indicating the image could not be successfully loaded.
     * @param errorRes 内容
     */
    void onLoadFailed(Drawable errorRes);

    /**
     * Callback invoked right before your request is submitted.
     */
    void onLoadStarted();

}
