package com.example.user.touchengtravelapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.touchengtravelapp.Funtional.BaseActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jude.swipbackhelper.SwipeBackHelper;


import java.nio.file.Path;
import java.util.Set;

public class DetailActivity extends BaseActivity {

    private Intent intent;
    private JsonObject data;
    private Uri PathUri;

    private int ImageID;
    private Set<String> keys;

    //Component
    private ImageView image;
    private RecyclerView list;
    private Toolbar toolbar;
    private TextView[] TextViews = new TextView[5];
    private LinearLayout detailPage;


    private int[] txvIds = {R.id.txv1, R.id.txv2, R.id.txv3, R.id.txv4, R.id.txv5};
    private int txvItem = 0;
    private Intent MapIntent;
    private ScrollView scrollView;


    //region Activity lifecycle

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        SwipeBackHelper.onCreate(this);
        setVariable();
        setupComponent();
        setupData();
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
//        showToastS("onDestroy");
        SwipeBackHelper.onDestroy(this);
    }
    //endregion

    //region Set Method

    private void setVariable() {
        //initVariable
        intent = getIntent();
        MapIntent = new Intent(Intent.ACTION_VIEW);
        MapIntent.setPackage("com.google.android.apps.maps");
        //IntentData
        data = GlobalMethod.getJsonObject(intent.getStringExtra("data"));
        ImageID = intent.getIntExtra("position", R.drawable.noimageavailable);
        keys = data.keySet();
        PathUri = GlobalMethod.getPathUri(data);

    }

    private void setupComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        detailPage = (LinearLayout) findViewById(R.id.detailpage);
        scrollView = (ScrollView) findViewById(R.id.scroll);
        image = (ImageView) findViewById(R.id.image);
        list = (RecyclerView) findViewById(R.id.list);
        
        for (int txvId : txvIds) {
            TextViews[txvId] = (TextView) findViewById(txvIds[txvId]);
        }
    }

    private void setupData() {
        if (keys.contains("Introduce")) {
            for (String key : keys) {
                if (!key.equals("name") &&
                        !key.equals("Introduce") &&
                        !key.equals("Route") &&
                        !key.equals("Attractions") &&
                        !key.equals("PathUrl"))
                    TextViews[txvItem++].append(data.get(key).getAsString());
            }
        } else detailPage.setVisibility(View.GONE);

        toolbar.setTitle(data.get("name").getAsString());
        scrollView.smoothScrollBy(0, 0);
        Glide.with(this).load(ImageID).into(image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapIntent.setData(PathUri);
                startActivity(MapIntent);
            }
        });
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        list.setAdapter(new Adapter(data.get("Attractions").getAsJsonArray()));

    }
    //endregion

    //region Implements Method


    //endregion

    //region Module Method

    //endregion


    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


        private JsonArray data;

        public Adapter(JsonArray data) {
            this.data = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_detail, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final JsonObject object = data.get(position).getAsJsonObject();
            final String location = object.get("location").getAsString();
            final String detail = object.get("detail").getAsString();
            holder.location.setText(location);
            holder.detail.setText(detail);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (location.startsWith("舊地名巡禮")) {
                        MapIntent.setData(Uri.parse("https://www.google.com.tw/maps/dir/262%E5%AE%9C%E8%98%AD%E7%B8%A3%E7%A4%81%E6%BA%AA%E9%84%89%E7%91%AA%E5%83%AF%E8%B7%AF/%E6%AD%A6%E6%9A%96%E7%9F%B3%E6%9D%BF%E6%A9%8B+260%E5%AE%9C%E8%98%AD%E7%B8%A3%E7%A4%81%E6%BA%AA%E9%84%89%E5%85%89%E6%AD%A6%E6%9D%91%E6%AD%A6%E6%9A%96%E8%B7%AF/%E5%BE%97%E5%AD%90%E5%8F%A3%E6%BA%AA+262%E5%AE%9C%E8%98%AD%E7%B8%A3%E7%A4%81%E6%BA%AA%E9%84%89/@24.8018806,121.7432227,14z/data=!3m1!4b1!4m20!4m19!1m5!1m1!1s0x3467fae4635b07f3:0x58bde976571f040c!2m2!1d121.7717525!2d24.7934561!1m5!1m1!1s0x3467fb271fcbc8c1:0x4fbe49a469239984!2m2!1d121.7623629!2d24.782105!1m5!1m1!1s0x3467fbafce410469:0x621cdcc9774c1d80!2m2!1d121.7562333!2d24.8213671!3e0?hl=zh-TW"));
                    } else {
                        MapIntent.setData(Uri.parse("geo:0,0?q=" + object.get("search").getAsString()));
                    }
                    startActivity(MapIntent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView location;
            private final TextView detail;

            public ViewHolder(View view) {
                super(view);
                location = (TextView) view.findViewById(R.id.location);
                detail = (TextView) view.findViewById(R.id.detail);

            }
        }
    }

}
