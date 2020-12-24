package com.example.user.touchengtravelapp.Funtional;

import com.example.user.touchengtravelapp.GlobalVariable;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

/**
 * Created by user on 2018/9/8.
 */

public class MainSliderAdapter extends SliderAdapter {
    private int type;
    private int[] Images = GlobalVariable.ViewPointImages;

    public MainSliderAdapter(int pos) {
        this.type = 5 * pos;
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        viewHolder.bindImageSlide(Images[type + position]);
    }
}












