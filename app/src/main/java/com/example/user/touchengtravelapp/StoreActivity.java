package com.example.user.touchengtravelapp;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.touchengtravelapp.Funtional.BaseActivity;
import com.google.gson.JsonObject;
import com.jude.swipbackhelper.SwipeBackHelper;

public class StoreActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "StoreActivity";

    private Intent intent;
    private JsonObject data;
    private String S_title;
    private String S_phone;
    private String S_address;
    private String S_time;
    private String S_discount;

    private int ImageID;

    private Toolbar toolbar;
    private ImageView imageView;
    private TextView title;
    private RelativeLayout search;
    private RelativeLayout call;
    private RelativeLayout location;
    private TextView phone;
    private TextView address;
    private TextView time;
    private TextView discount;
    private String S_email;


    //region Activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        SwipeBackHelper.onCreate(this);
        setVariable();
        setupComponent();
        setupListener();

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
        intent = getIntent();
        ImageID = intent.getIntExtra("Image", R.drawable.noimageavailable);
        data = GlobalMethod.getJsonObject(intent.getStringExtra("data"));

        S_title = data.get("名稱").getAsString();
        S_email = data.get("聯絡人電郵").getAsString();
        S_phone = data.get("電話").getAsString();
        S_address = data.get("地址").getAsString();
        S_time = data.get("營業時間").getAsString();
        S_discount = data.get("優惠").getAsString();
    }

    private void setupComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (ImageView) findViewById(R.id.image);

        title = (TextView) findViewById(R.id.title);

        search = (RelativeLayout) findViewById(R.id.search);
        call = (RelativeLayout) findViewById(R.id.call);
        location = (RelativeLayout) findViewById(R.id.location);

        phone = (TextView) findViewById(R.id.phone);
        address = (TextView) findViewById(R.id.address);
        time = (TextView) findViewById(R.id.time);
        discount = (TextView) findViewById(R.id.discount);

    }

    private void setupListener() {
        toolbar.setTitle(S_title);
        setSupportActionBar(toolbar);

        Glide.with(this).load(ImageID).into(imageView);

        title.setText(data.get("名稱").getAsString());

        search.setOnClickListener(this);
        call.setOnClickListener(this);
        location.setOnClickListener(this);

        phone.append(S_phone);
        address.append(S_address);
        time.setText(S_time);
        discount.setText(S_discount);

    }
    //endregion

    //region Implements Method
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                startActivity(new Intent(Intent.ACTION_WEB_SEARCH).putExtra(SearchManager.QUERY, S_title));
                break;
            case R.id.call:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + S_phone)));
                break;
            case R.id.location:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + S_address)));
                break;
        }
    }
    //endregion

    //region Module Method

    //endregion

}
