package com.example.user.touchengtravelapp.Funtional;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.user.touchengtravelapp.GlobalVariable;

/**
 * Created by user on 2018/9/18.
 */

public class GpsUtil {

    private static final String TAG = "GpsUtil";
    static final int MIN_TIME = 5000;
    static final float MIN_DIST = 0;
    private static LocationManager locationManager;
    private static boolean gps = false;
    private static boolean network = false;
    private static LocationListener locationListener;

    /**
     * 判斷GPS是否開啟，GPS或者AGPS開啟一個就認為是開啟的
     *
     * @param context
     * @return true 表示開啟
     */
    public static final boolean isOPen(final Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通過GPS衛星定位，定位級別可以精確到街（通過24顆衛星定位，在室外和空曠的地方定位準確、速度快）
        gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通過WLAN或移動網絡(3G/2G)確定的位置（也稱作AGPS，輔助GPS定位。主要用於在室內或遮蓋物（建築群或茂密的深林等）密集的地方定位）
        network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            Log.d(TAG, "isOPen true");
            return true;
        }
        Log.d(TAG, "isOPen false");
        return false;
    }


    /**
     * 幫用戶打開GPS
     *
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        Toast.makeText(context,"抱歉!!!由於藍芽iBeacon集點功能須開啟定位否則無法進行集點，敬請見諒!!",Toast.LENGTH_LONG).show();
        context.startActivity(GPSIntent);
    }
}
