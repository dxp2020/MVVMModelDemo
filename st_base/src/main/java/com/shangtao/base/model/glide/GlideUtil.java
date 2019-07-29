package com.shangtao.base.model.glide;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.shangtao.base.BaseApplication;
import com.shangtao.base.model.file.FileCacheUtils;
import com.shangtao.base.model.utils.L;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Glide工具
 */
public class GlideUtil {

    private static String baseUrl = "";

    public static void setBaseUrl(String baseUrl) {
        GlideUtil.baseUrl = baseUrl;
    }

    public static String checkUrl(String url) {
        if (url == null) {
            url = "";
        }
        if (!new File(url).isFile() && !url.startsWith("http")) {
            url = baseUrl + url;
        }
        return url;
    }

    /**
     * 加载图片
     *
     * @param url 网络路径或本地路径
     */
    public static void loadImage(ImageView imageView, String url) {
        url = checkUrl(url);
        Glide.with(imageView.getContext()).load(url).dontAnimate().into(imageView);
    }

    /**
     * 加载图片
     *
     * @param url 网络路径或本地路径
     */
    public static void loadImageNoFlash(ImageView imageView, String url) {
        url = checkUrl(url);
        Glide.with(imageView.getContext()).load(url).skipMemoryCache(false).dontAnimate().into(imageView);
    }

    /**
     * 加载图片
     *
     * @param url 网络路径或本地路径
     */
    public static void loadImage(ImageView imageView, String url, ImageView.ScaleType scaleType) {
        url = checkUrl(url);
        imageView.setScaleType(scaleType);
        Glide.with(imageView.getContext()).load(url).dontAnimate().into(imageView);
    }

    /**
     * 加载图片
     *
     * @param url 网络路径或本地路径
     */
    public static void loadImage(ImageView imageView, String url, int ids ,ImageView.ScaleType scaleType) {
        url = checkUrl(url);
        imageView.setScaleType(scaleType);
        Glide.with(imageView.getContext()).load(url).dontAnimate().placeholder(ids).error(ids).into(imageView);
    }

    /**
     * 加载图片
     *
     * @param url 网络路径或本地路径
     */
    public static void loadImage(Activity activity,ImageView imageView, String url, int ids ,ImageView.ScaleType scaleType) {
        url = checkUrl(url);
        Glide.with(activity).load(url)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        //展示图片
                        imageView.setImageDrawable(resource);
                        imageView.setScaleType(scaleType);
                    }

                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        imageView.setImageResource(ids);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        imageView.setImageResource(ids);
                    }
                });
    }

    /**
     * 加载图片
     *
     * @param url 网络路径或本地路径
     */
    public static void loadImage(ImageView imageView, String url, int placeholderRes) {
        url = checkUrl(url);
        Glide.with(imageView.getContext()).load(url).dontAnimate().placeholder(placeholderRes).error(placeholderRes).into(imageView);
    }

    /**
     * 加载图片
     *
     * @param url 网络路径或本地路径
     */
    public static void loadImage(Activity mActivity,ImageView imageView, String url, int ids) {
        url = checkUrl(url);
        Glide.with(mActivity).load(url).dontAnimate().placeholder(ids).error(ids).into(imageView);
    }

    /**
     * 加载图片
     *
     * @param url      网络路径或本地路径
     * @param listener 加载过程监听
     */
    public static void loadImage(ImageView imageView, String url, RequestListener<? super String, GlideDrawable> listener) {
        url = checkUrl(url);
        Glide.with(imageView.getContext()).load(url).listener(listener).into(imageView);
    }

    /**
     * 加载图片
     *
     * @param url      网络路径或本地路径
     * @param listener 加载完成回调
     */
    public static void loadImage(Context context, String url, SimpleTarget<Bitmap> listener) {
        url = checkUrl(url);
        Glide.with(context).load(url).asBitmap().into(listener);
    }

    /**
     * 加载图片
     *
     * @param url      网络路径或本地路径
     * @param listener 加载完成回调
     */
    public static void loadRoundImage(Context context, String url, int dp, SimpleTarget<Bitmap> listener) {
        url = checkUrl(url);
        Glide.with(context).load(url).asBitmap().transform(new TransformRound(context, dp)).into(listener);
    }

    /**
     * 加载图片
     *
     * @param resId    网络路径或本地路径
     * @param listener 加载过程监听
     */
    public static void loadImage(ImageView imageView, int resId, RequestListener<? super Integer, GlideDrawable> listener) {
        Glide.with(imageView.getContext()).load(resId).listener(listener).into(imageView);
    }

    /**
     * 加载图片
     *
     * @param url      网络路径或本地路径
     * @param listener 加载完成回调
     */
    public static void loadRoundImage(String url, int dp, SimpleTarget<Bitmap> listener) {
        url = checkUrl(url);
        Glide.with(BaseApplication.getApplication()).load(url).asBitmap().transform(new TransformRound(BaseApplication.getApplication(), dp)).into(listener);
    }

    /**
     * 加载图片，并将图片变为圆形
     *
     * @param url 网络路径或本地路径
     */
    public static void loadCircleImage(ImageView imageView, String url) {
        url = checkUrl(url);
        Context context = imageView.getContext();
        Glide.with(context).load(url).transform(new TransformCircle(context)).into(imageView);
    }

    public static void loadCircleImage(ImageView imageView, int resId) {
        Context context = imageView.getContext();
        Glide.with(context).load(resId).transform(new TransformCircle(context)).into(imageView);
    }


    /**
     * @param url 网络路径或本地路径
     */
    public static void loadDefault(ImageView imageView, int defaultResId, String url) {
        url = checkUrl(url);
        Context context = imageView.getContext();
        Glide.with(context).load(url)
                .placeholder(defaultResId)
                .error(defaultResId)
                .into(imageView);
    }

    /**
     * @param url 网络路径或本地路径
     */
    public static void loadDefault(Fragment fragment, ImageView imageView, int defaultResId, String url) {
        url = checkUrl(url);
        Glide.with(fragment).load(url)
                .placeholder(defaultResId)
                .error(defaultResId)
                .into(imageView);
    }

    /**
     * 加载图片，并将图片变为圆形
     *
     * @param url 网络路径或本地路径
     */
    public static void loadCircleDefault(ImageView imageView, int defaultResId, String url) {
        url = checkUrl(url);
        Context context = imageView.getContext();
        Glide.with(context).load(url).transform(new TransformCircle(context))
                .placeholder(defaultResId)
                .error(defaultResId)
                .into(imageView);
    }

    /**
     * 加载图片，并将图片变为圆形
     *
     * @param url 网络路径或本地路径
     */
    public static void loadCircleDefault(Fragment fragment, ImageView imageView, int defaultResId, String url) {
        url = checkUrl(url);
        Context context = imageView.getContext();
        Glide.with(fragment).load(url).transform(new TransformCircle(context))
                .placeholder(defaultResId)
                .error(defaultResId)
                .into(imageView);
    }


    /**
     * 加载图片，并将图片变为圆形
     *
     * @param url 网络路径或本地路径
     */
    public static void loadAvatar(ImageView imageView, String url,int placeholderId,int errorId) {
        url = checkUrl(url);
        Context context = imageView.getContext();
        Glide.with(context).load(url).transform(new TransformCircle(context))
                .placeholder(placeholderId)
                .error(errorId)
                .into(imageView);
    }

    /**
     * 加载图片，并将图片变为圆形
     *
     * @param url 网络路径或本地路径
     */
    public static void loadAvatar(ImageView imageView, String url, int defaultId) {
        url = checkUrl(url);
        Context context = imageView.getContext();
        Glide.with(context).load(url).transform(new TransformCircle(context))
                .placeholder(defaultId)
                .error(defaultId)
                .into(imageView);
    }

    /**
     * 加载图片，并将图片变为圆角
     *
     * @param url 网络路径或本地路径
     * @param dp  圆角的大小，单位dp
     */
    public static void loadRoundImage(ImageView imageView, String url, int dp) {
        url = checkUrl(url);
        Context context = imageView.getContext();
        Glide.with(context).load(url).transform(new TransformRound(context, dp)).into(imageView);
    }

    /**
     * 加载图片，并将图片变为圆角
     *
     * @param url 网络路径或本地路径
     */
    public static void loadRoundImage(ImageView imageView, String url,int width,int height) {
        url = checkUrl(url);
        Context context = imageView.getContext();
        Glide.with(context).load(url).override(width,height).centerCrop().into(imageView);
    }

    /**
     * 加载图片，并将图片变为圆角
     *
     * @param dp 圆角的大小，单位dp
     */
    public static void loadRoundImage(ImageView imageView, byte[] data, int dp, RequestListener<? super byte[], GlideDrawable> listener) {
        Context context = imageView.getContext();
        Glide.with(context).load(data).transform(new TransformRound(context, dp)).listener(listener).into(imageView);
    }

    /**
     * 加载图片
     */
    public static void loadImage(ImageView imageView, byte[] data, RequestListener<? super byte[], GlideDrawable> listener) {
        Context context = imageView.getContext();
        Glide.with(context).load(data).listener(listener).into(imageView);
    }

    public static void loadRoundImage(ImageView imageView, int resId, int dp) {
        Context context = imageView.getContext();
        Glide.with(context).load(resId).transform(new TransformRound(context, dp)).into(imageView);
    }

    /**
     * 下载图片并保存到本地
     *
     * @param fileName
     * @param url
     */
    public static void loadPicToLocal(String fileName, String url, ImageDownloadListener pImageDownloadListener) {
        url = checkUrl(url);
        Glide.with(BaseApplication.getApplication()).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                savaFileToSD(fileName, resource, pImageDownloadListener);
            }
        });
    }

    public static String savaFileToSD(String fileName, Bitmap bmp) {
        return savaFileToSD(fileName,bmp,null);
    }

    private static String savaFileToSD(String fileName, Bitmap bmp, ImageDownloadListener pImageDownloadListener) {
        String filePath = FileCacheUtils.getImageDir(BaseApplication.getApplication()).getAbsolutePath();
        try {
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            fileName = filePath + "/" + fileName;
            FileOutputStream fos = new FileOutputStream(fileName);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            //关闭输出流
            L.e("图片已成功保存到" + fileName);
            if (pImageDownloadListener!=null) {
                pImageDownloadListener.onCompleted(fileName);
            }
            return fileName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public interface ImageDownloadListener {
        void onCompleted(String fileName);
    }

    public static void onStop(@NonNull Fragment context) {
        Glide.with(context).onStop();
    }

    public static void clearMemory(@NonNull Context c) {
        Glide.get(c).clearMemory();
    }
}
