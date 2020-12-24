package com.example.user.touchengtravelapp.Fragment;

import android.content.Context;
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
import android.widget.LinearLayout;

import com.example.user.touchengtravelapp.GlobalVariable;
import com.example.user.touchengtravelapp.Funtional.RouteAdapter;
import com.example.user.touchengtravelapp.R;
import com.google.gson.JsonArray;

/**
 * Created by user on 2018/7/23.
 */

public class Fragment_a路線導覽 extends Fragment {

    private View view;
    private LinearLayout page達人;
    private LinearLayout page自由行;
    private BottomNavigationView navigation;
    private RecyclerView list達人;
    private RecyclerView list自由行;
    private JsonArray data達人;
    private JsonArray data自由行;
    private Context context;
    private GlobalVariable globalVariable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_route, container, false);
        setupComponent();
        setupListener();
        setRecyclerView();
        return view;
    }
    private void setupComponent() {
        page達人 = (LinearLayout) view.findViewById(R.id.page達人);
        page自由行 = (LinearLayout) view.findViewById(R.id.page自由行);
        list達人 = (RecyclerView) view.findViewById(R.id.list達人);
        list自由行 = (RecyclerView) view.findViewById(R.id.list自由行);
        context = getContext();
        globalVariable = (GlobalVariable) context.getApplicationContext();

    }

    private void setupListener() {
        data達人 = globalVariable.data達人();
        data自由行 = globalVariable.data自由行();

        navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_達人推薦:
                        page達人.setVisibility(View.VISIBLE);
                        page自由行.setVisibility(View.GONE);
                        break;
                    case R.id.navigation_自由行:
                        page達人.setVisibility(View.GONE);
                        page自由行.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });
    }

    private void setRecyclerView() {
        list達人.setLayoutManager(new LinearLayoutManager(list達人.getContext()));
        list達人.setAdapter(new RouteAdapter(data達人, context, GlobalVariable.TalentImages));

        list自由行.setLayoutManager(new LinearLayoutManager(list自由行.getContext()));
        list自由行.setAdapter(new RouteAdapter(data自由行, context, GlobalVariable.FreeImages));

    }

}
