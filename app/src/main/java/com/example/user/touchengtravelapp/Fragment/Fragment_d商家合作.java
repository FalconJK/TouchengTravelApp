package com.example.user.touchengtravelapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.touchengtravelapp.GlobalVariable;
import com.example.user.touchengtravelapp.R;
import com.example.user.touchengtravelapp.StoreActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 2018/7/23.
 */

public class Fragment_d商家合作 extends Fragment {

    private String Tag = "Fragment_d商家合作";
    private View view;
    private Context context;
    private JsonArray data店家;
    private JsonArray data館舍;
    private RecyclerView list館舍;
    private RecyclerView list店家;
    private BottomNavigationView navigation;
    private int[] image館舍;
    private int[] image店家;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_store, container, false);
        setVariable();
        setupComponent();
        setRecyclerView();
        return view;
    }

    private void setVariable() {
        context = getContext();
        data館舍 = GlobalVariable.data館舍;
        data店家 = GlobalVariable.data店家;
        image館舍 = GlobalVariable.MuseumImages;
        image店家 = GlobalVariable.StoreImages;
    }

    private void setupComponent() {
        list館舍 = (RecyclerView) view.findViewById(R.id.list館舍);
        list店家 = (RecyclerView) view.findViewById(R.id.list店家);

        navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_達人推薦:
                        list館舍.setVisibility(View.VISIBLE);
                        list店家.setVisibility(View.GONE);
                        break;
                    case R.id.navigation_自由行:
                        list館舍.setVisibility(View.GONE);
                        list店家.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });
    }

    private void setRecyclerView() {
        list館舍.setLayoutManager(new LinearLayoutManager(context));
        list店家.setLayoutManager(new LinearLayoutManager(context));

        list館舍.setAdapter(new Adapter(data館舍, image館舍));
        list店家.setAdapter(new Adapter(data店家, image店家));
    }


    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private JsonArray data;
        private int[] Images;

        public Adapter(JsonArray data, int[] images) {
            this.data = data;
            Images = images;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_store, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final JsonObject object = data.get(position).getAsJsonObject();
            holder.name.setText(object.get("名稱").getAsString());
            holder.address.setText(object.get("地址").getAsString());
            holder.time.setText(object.get("營業時間").getAsString());
            Glide.with(holder.itemView.getContext()).load(Images[position]).into(holder.image);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(holder.itemView.getContext(), StoreActivity.class)
                            .putExtra("Image", Images[position])
                            .putExtra("data", object.toString()));
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView name;
            private final TextView time;
            private final TextView address;
            private final ImageView image;

            public ViewHolder(View view) {
                super(view);
                image = (ImageView) view.findViewById(R.id.image);
                name = (TextView) view.findViewById(R.id.name);
                time = (TextView) view.findViewById(R.id.time);
                address = (TextView) view.findViewById(R.id.address);
            }
        }
    }

}
