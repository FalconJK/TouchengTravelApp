package com.example.user.touchengtravelapp;


import android.net.Uri;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Created by user on 2018/9/9.
 */

public class GlobalMethod {

    //region getJsonObject

    public static JsonObject getJsonObject(String string) {
        try {
            return new JsonParser().parse(string).getAsJsonObject();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getStringfromJsonObject(JsonObject jsonObject, String string) {
        try {
            return jsonObject.get(string).getAsString();
        } catch (Exception e) {
            return null;
        }
    }
    //endregion

    //region getJsonArray

    public static JsonArray getJsonArray(String string) {
        try {
            return new JsonParser().parse(string).getAsJsonArray();
        } catch (Exception e) {
            return null;
        }
    }

    public static JsonObject getObjectfromArray(JsonArray array, int position) {
        try {
            return array.get(position).getAsJsonObject();
        } catch (Exception e) {
            return null;
        }
    }

    //endregion

    //region PathUri

    public static Uri getPathUri(JsonObject object) {
        JsonArray array = object.get("Attractions").getAsJsonArray();
        ArrayList<String> arrayList = new ArrayList<>();
        StringBuilder MapUrl = new StringBuilder("https://maps.google.com/maps?daddr=");
        for (JsonElement jsonElement : array) {
            MapUrl.append("+to:" + jsonElement.getAsJsonObject().get("search").getAsString());
        }
        return Uri.parse(MapUrl.toString());
    }


    //endregion

}
