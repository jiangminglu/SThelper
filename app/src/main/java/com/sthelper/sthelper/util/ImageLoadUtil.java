package com.sthelper.sthelper.util;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sthelper.sthelper.R;

/**
 * Created by luffy on 15/7/6.
 */
public class ImageLoadUtil {

    /**
     * 获取圆形头像
     *
     * @param imageView 头像imageview
     * @param url       图片的url
     */
    public static void getCircleAvatarImage(ImageView imageView, String url) {
        if (url == null || "".equals(url)) return;
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_avatar)
                .showImageForEmptyUri(R.mipmap.default_avatar)
                .showImageOnFail(R.mipmap.default_avatar)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .considerExifParams(true)
                .displayer(new CircleBitmapDisplayer())
                .build();

        imageLoader.displayImage(url, imageView, options);
    }

    public static void getCommonImage(ImageView imageView, String url) {
        if (url == null || "".equals(url)) return;
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_avatar)
                .showImageForEmptyUri(R.mipmap.default_avatar)
                .showImageOnFail(R.mipmap.default_avatar)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .considerExifParams(true)
                .build();

        imageLoader.displayImage(url, imageView, options);
    }
}

