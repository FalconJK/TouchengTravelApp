package com.example.user.touchengtravelapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.touchengtravelapp.Funtional.BaseActivity;
import com.example.user.touchengtravelapp.Funtional.PicassoImageLoadingService;
import com.example.user.touchengtravelapp.Funtional.MainSliderAdapter;
import com.jude.swipbackhelper.SwipeBackHelper;

import ss.com.bannerslider.Slider;
import ss.com.bannerslider.event.OnSlideClickListener;

//import com.bumptech.glide.Glide;

public class PointActivity extends BaseActivity {

    private TextView name;
    private TextView introduce;
    private Intent intent;
    private String point;
    private String introduces;
    private int pos;
    private Slider slider;


    //region Activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        SwipeBackHelper.onCreate(this);
        setVariable();
        setupComponent();
        setupListener();

        name.setText(point);
        introduce.setText(introduces);
//        Glide.with(this).load(ViewPointImages[5 * pos]).into(imageView);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }
    //endregion

    //region Set Method

    private void setVariable() {
        Slider.init(new PicassoImageLoadingService(this));
        intent = getIntent();
        pos = intent.getIntExtra("position", 0);
        point = intent.getStringExtra("point");
        introduces = intent.getStringExtra("introduce");
    }

    private void setupComponent() {
        name = (TextView) findViewById(R.id.name);
        introduce = (TextView) findViewById(R.id.introduce);
        slider = (Slider) findViewById(R.id.slider);
        slider.setAdapter(new MainSliderAdapter(pos));
    }

    private void setupListener() {
        slider.setOnSlideClickListener(new OnSlideClickListener() {
            @Override
            public void onSlideClick(int position) {

            }
        });
    }

    //endregion




}
