package com.example.user.touchengtravelapp;

import android.app.Application;
import android.content.Context;

import com.example.user.touchengtravelapp.Funtional.StreamUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by user on 2018/8/28.
 */

public class GlobalVariable extends Application {

    public static boolean[] Detected = {false, false, false, false, false, false};

    public static String getLocation() {
        return Location;
    }

    public static void setLocation(String location) {
        Location = location;
    }

    public static String Location = "";

    public static JsonArray data店家 = new JsonArray();
    public static JsonArray data館舍 = new JsonArray();

    //景點照片
    //region public static int[] ViewPointImages
    public static int[] ViewPointImages = {
            R.drawable.a1,
            R.drawable.a2,
            R.drawable.a3,
            R.drawable.a4,
            R.drawable.a5,

            R.drawable.b1,
            R.drawable.b2,
            R.drawable.b3,
            R.drawable.b4,
            R.drawable.b5,

            R.drawable.c1,
            R.drawable.c2,
            R.drawable.c3,
            R.drawable.c4,
            R.drawable.c5,

            R.drawable.d1,
            R.drawable.d2,
            R.drawable.d3,
            R.drawable.d4,
            R.drawable.d5,

            R.drawable.e1,
            R.drawable.e2,
            R.drawable.e3,
            R.drawable.e4,
            R.drawable.e5,

            R.drawable.f1,
            R.drawable.f2,
            R.drawable.f3,
            R.drawable.f4,
            R.drawable.f5,
    };
    //endregion


    //region public static int[] TalentImages
    public static int[] TalentImages = {
            R.drawable.talentimages1,
            R.drawable.talentimages2,
            R.drawable.talentimages3,
            R.drawable.talentimages4,
            R.drawable.talentimages5,
    };
    //endregion


    //region public static int[] FreeImages
    public static int[] FreeImages = {
            R.drawable.imagefree1,
            R.drawable.imagefree2,
            R.drawable.imagefree3,
            R.drawable.imagefree4,
            R.drawable.imagefree5
    };
    //endregion


    //region public static int[] MuseumImages
    public static int[] MuseumImages = {
            R.drawable.m1,
            R.drawable.m2,
            R.drawable.m3,
            R.drawable.m4,
            R.drawable.m5,
            R.drawable.m6,
            R.drawable.m7,
            R.drawable.m8,
            R.drawable.m9,
            R.drawable.m10,
            R.drawable.m11,
            R.drawable.m12,
            R.drawable.m13,
            R.drawable.m14,
            R.drawable.m15,
            R.drawable.m16,
            R.drawable.m17
    };
    //endregion


    //region public static int[] StoreImages
    public static int[] StoreImages = {
            R.drawable.s1,
            R.drawable.s2,
            R.drawable.s3,
            R.drawable.s4,
            R.drawable.s5,
            R.drawable.s6,
            R.drawable.s7,
            R.drawable.s8,
            R.drawable.s9,
            R.drawable.s10,
            R.drawable.s11
    };
    //endregion

    @Override
    public void onCreate() {
        super.onCreate();
        JsonArray data = data店家();
        for (JsonElement datum : data) {
            JsonObject object = datum.getAsJsonObject();
            String id = GlobalMethod.getStringfromJsonObject(object, "ID");
            switch (id.substring(0, 1)) {
                case "A":
                    data館舍.add(object);
                    break;
                case "B":
                    data店家.add(object);
                    break;
            }
        }
    }

    //region data達人
    public JsonArray data達人() {
        return data達人(getApplicationContext());
    }


    public static JsonArray data達人(Context context) {
        return GlobalMethod.getJsonArray(StreamUtils.get(context, R.raw.greatpeople));
    }
//endregion


    //region data自由行
    public JsonArray data自由行() {
        return data自由行(getApplicationContext());
    }


    public static JsonArray data自由行(Context context) {
        return GlobalMethod.getJsonArray(StreamUtils.get(context, R.raw.free));

    }

    //endregion


    //region data店家
    public JsonArray data店家() {
        return data店家(getApplicationContext());
    }


    public static JsonArray data店家(Context context) {
        return GlobalMethod.getJsonArray(StreamUtils.get(context, R.raw.store));

    }

    //endregion


    //region data景點

    public JsonArray data景點() {
        return data景點(getApplicationContext());
    }


    public static JsonArray data景點(Context context) {
        return GlobalMethod.getJsonArray(StreamUtils.get(context, R.raw.pointintroduce));

    }


    //endregion


    //region Getter & Setter


    //boolean[] Detected Getter
    public boolean isDetected(int minor) {
        return Detected[minor];
    }

    //boolean[] Detected Setter
    public void setDetected(int minor) {
        Detected[minor] = true;
    }

    public void clearDetected() {

//        Detected = clear;
        for (int i = 0; i < Detected.length; i++) {
            Detected[i] = false;
        }
    }


    //endregion


}
