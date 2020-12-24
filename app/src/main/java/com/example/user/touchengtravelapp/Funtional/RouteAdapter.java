package com.example.user.touchengtravelapp.Funtional;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.touchengtravelapp.DetailActivity;
import com.example.user.touchengtravelapp.GlobalVariable;
import com.example.user.touchengtravelapp.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


/**
 * Created by user on 2018/8/4.
 */

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {


    private Context context;
    public JsonArray data;
    private Intent intent;
    private int[] Images;

    public RouteAdapter(JsonArray data, Context context, int[] images) {
        this.context = context;
        this.data = data;
        Images = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_route, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final JsonObject object = data.get(position).getAsJsonObject();

        holder.Name.setText(object.get("name").getAsString());
        Glide.with(holder.imageView.getContext()).load(Images[position]).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, DetailActivity.class);

                intent.putExtra("position", Images[position]);
                intent.putExtra("data", object.toString());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView Name;

        public ViewHolder(View view) {
            super(view);
            Name = (TextView) view.findViewById(R.id.name);
            imageView =(ImageView) view.findViewById(R.id.image);
        }
    }


}
