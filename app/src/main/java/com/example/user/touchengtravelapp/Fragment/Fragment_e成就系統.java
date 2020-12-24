package com.example.user.touchengtravelapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.user.touchengtravelapp.Funtional.PointData;
import com.example.user.touchengtravelapp.GlobalVariable;
import com.example.user.touchengtravelapp.R;

/**
 * Created by user on 2018/7/23.
 */

public class Fragment_e成就系統 extends Fragment {

    private View view;
    private int[] PointIds = {R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5, R.id.point6};
    private LinearLayout[] Points = new LinearLayout[6];
    private PointData pointData;
    private Button clear;
    private GlobalVariable globalVariable;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_achievement, container, false);
        setupComponent();

        return view;
    }

    private void setupComponent() {
        globalVariable = (GlobalVariable)getContext().getApplicationContext();
        pointData = new PointData(getContext());
        //TextView設定
        setupPoint();
        clear = (Button) view.findViewById(R.id.clear);


        clear.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                pointData.clear();
                for (int i = 0; i < Points.length; i++) {
                    Points[i].setBackgroundColor(0x00000000);
                    globalVariable.clearDetected();
                }
            }
        });

    }

    private void setupPoint() {
        for (int i = 0; i < Points.length; i++) {
            Points[i] = (LinearLayout) view.findViewById(PointIds[i]);
            if (pointData.getPointData(i))
                Points[i].setVisibility(View.VISIBLE);
        }
    }
}


















