package com.example.user.touchengtravelapp.Funtional;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by example on 16/9/2.
 */

public class PointData {
    private Context context;
    private static final String SHARED_PREF_LOGIN = "login";
    private boolean[] keys = new boolean[6];


    public PointData(Context context) {
        this.context = context;
    }


    public void setPointData(Integer pos) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(pos.toString(), true);
        editor.commit();
    }

    public boolean getPointData(int i) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_LOGIN, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(i + "", false);
    }

    public void clear() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < 6; i++)
            editor.putBoolean(i + "", false);
        editor.commit();
    }
}
