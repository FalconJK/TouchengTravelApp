package com.example.user.touchengtravelapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.touchengtravelapp.GlobalVariable;
import com.example.user.touchengtravelapp.PointActivity;
import com.example.user.touchengtravelapp.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by user on 2018/7/23.
 */

public class Fragment_b景點介紹 extends Fragment {

    private View view;
    private Context context;
    private GlobalVariable global;
    private JsonArray data景點;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_location, container, false);
        context = getContext();
        global = (GlobalVariable) context.getApplicationContext();
        data景點 = global.data景點();
        setRecyclerView();
        return view;
    }

    private void setRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.list景點);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new Adapter(data景點));
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private JsonArray data;

        public Adapter(JsonArray data) {
            this.data = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_point, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            JsonObject object = data.get(position).getAsJsonObject();
            final String point = object.get("point").getAsString();
            final String introduce = object.get("introduce").getAsString();

            holder.point.setText(point);
            holder.introduce.setText(introduce);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PointActivity.class);
                    intent.putExtra("point", point);
                    intent.putExtra("introduce", introduce);
                    intent.putExtra("position", position);

                    startActivity(intent);
                }
            });
            Glide.with(context).load(GlobalVariable.ViewPointImages[5 * position]).into(holder.imageView);


            if (introduce.isEmpty())
                holder.itemView.setClickable(false);

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView point;
            private final TextView introduce;
            private final ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                point = view.findViewById(R.id.Point);
                introduce = view.findViewById(R.id.introduce);
                imageView = view.findViewById(R.id.image);
            }
        }
    }

}
