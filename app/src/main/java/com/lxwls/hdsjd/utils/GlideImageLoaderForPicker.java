package com.lxwls.hdsjd.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;

/**
 * Created by Administrator on 2017/8/30.
 */

public class GlideImageLoaderForPicker implements ImageLoader{

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {

        Glide.with(activity)
                .load(path)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
