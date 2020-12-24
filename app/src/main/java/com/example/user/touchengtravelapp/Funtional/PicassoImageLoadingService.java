package com.example.user.touchengtravelapp.Funtional;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import ss.com.bannerslider.ImageLoadingService;

/**
 * Created by user on 2018/9/8.
 */

public class PicassoImageLoadingService implements ImageLoadingService {
    private Context context;

    public PicassoImageLoadingService(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop();
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop();
        Glide.with(context).load(resource).apply(options).into(imageView);
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeHolder)
                .error(errorDrawable);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

}
