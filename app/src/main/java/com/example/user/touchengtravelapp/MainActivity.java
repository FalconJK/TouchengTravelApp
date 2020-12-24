package com.example.user.touchengtravelapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.THLight.USBeacon.App.Lib.iBeaconData;
import com.example.user.touchengtravelapp.Fragment.Fragment_d商家合作;
import com.example.user.touchengtravelapp.Fragment.Fragment_e成就系統;
import com.example.user.touchengtravelapp.Fragment.Fragment_b景點介紹;
import com.example.user.touchengtravelapp.Fragment.Fragment活動訊息;
import com.example.user.touchengtravelapp.Fragment.Fragment_a路線導覽;
import com.example.user.touchengtravelapp.Funtional.BaseActivity;
import com.example.user.touchengtravelapp.Funtional.GpsUtil;
import com.example.user.touchengtravelapp.Funtional.PointData;
import com.example.user.touchengtravelapp.Funtional.ProgressHelper;

import mbanje.kurt.fabbutton.FabButton;

@SuppressLint("NewApi")
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, BluetoothAdapter.LeScanCallback {

    //region Variable
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 2;
    private static final String Tag = "MainActivity";
    private String url = "https://asiayo.com/zh-tw/list/tw/yilan-county/spot/lan-yang-museum/";
    private static final long SCAN_PERIOD = 10000;
    //endregion

    private boolean Scanning = false;
    private Handler mHandler;
    private int FabColor = Color.parseColor("#1976D2");
    private int CurrentId;
    private int ResId;
    private int[] NavId = {R.id.nav_route, R.id.nav_location, R.id.nav_room, R.id.nav_store, R.id.nav_achievement};


    //region Component
    private Toolbar toolbar;
    private FabButton fab;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    //    endregion

    private GlobalVariable globalVariable;
    private iBeaconData ib;
    private BluetoothAdapter mBluetoothAdapter;
    private PointData pointData;
    private ProgressDialog dialog;
    private ProgressHelper helper;
    private Runnable runnable;


    //region Activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVariable();
        setupComponent();
        setupListener();
    }

    @Override
    protected void onStart() {
//        showToastS("onStart");
        super.onStart();
        setBeaconScan();
        setPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dialog != null)
            dialog.dismiss();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mBluetoothAdapter != null)
            if (mBluetoothAdapter.isEnabled())
                mBluetoothAdapter.disable();

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanLeDevice(false);
        globalVariable.clearDetected();

    }

    //endregion

    //region Set Method
    private void setVariable() {
        mHandler = new Handler();
        globalVariable = (GlobalVariable) getApplicationContext();
        pointData = new PointData(this);
        runnable = new Runnable() {
            @Override
            public void run() {
                Scanning = false;
                mBluetoothAdapter.stopLeScan(MainActivity.this);
                helper.startDeterminate();
            }
        };
    }

    private void setupComponent() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(navigationView.getMenu().getItem(0).getTitle());
        setSupportActionBar(toolbar);

        fab = (FabButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(FabColor));
        int icon = R.drawable.fab_ibeacon;
        fab.setIcon(icon, icon);
        helper = new ProgressHelper(fab, this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new Fragment_a路線導覽(), NavId[0]);

    }

    private void setupListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanLeDevice(true);
            }
        });
    }

    private void setPermission() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            showToastL("不支援");
            finish();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }
    }

    private void setBeaconScan() {
        // init bluetooth adapter
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
        if (!GpsUtil.isOPen(this)) {
            GpsUtil.openGPS(this);
        }
        if (mBluetoothAdapter.isEnabled() &&
                GpsUtil.isOPen(this)) {
            scanLeDevice(true);
        }//如果藍芽或GPS沒開就不要往下
    }

    //endregion

    //region Implements Method
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        ResId = item.getItemId();
        if (!(CurrentId == ResId))
            switch (ResId) {
                case R.id.nav_route:
                    replaceFragment(new Fragment_a路線導覽(), ResId);
                    break;
                case R.id.nav_location:
                    replaceFragment(new Fragment_b景點介紹(), ResId);
                    break;
                case R.id.nav_room:
                    dialog = ProgressDialog.show(this, "請稍後",
                            "Loading. Please wait...", true);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
//                    replaceFragment(new Fragment_c住宿資訊());
                    break;
                case R.id.nav_store:
                    replaceFragment(new Fragment_d商家合作(), ResId);
                    break;
                case R.id.nav_achievement:
                    replaceFragment(new Fragment_e成就系統(), ResId);
                    break;
                case R.id.nav_message:
                    replaceFragment(new Fragment活動訊息(), ResId);
                    break;
            }
        if (ResId != R.id.nav_room) {
            CurrentId = ResId;
            toolbar.setTitle(item.getTitle());
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

//        BatteryPowerData bData = BatteryPowerData.generateBatteryBeacon(scanRecord);
//        if (bData != null
//                && device.getName() != null
//                && bData.BatteryUuid.equals("b2c65c77e44948f292b887b5ad7f0126")) {
//
//        }
        ib = iBeaconData.generateiBeacon(scanRecord);
        if (ib != null
                && device.getName() != null
                && device.getName().startsWith("USB")) {
            if (ib.major == 250) {
                if (ib.minor >= 0 && ib.minor <= 5) {
                    Log.d(Tag, ib.minor + " : " + globalVariable.Detected[ib.minor] + "");
                    if (!globalVariable.isDetected(ib.minor)) {
                        //切換Fragment
                        replaceFragment(new Fragment_e成就系統(), NavId['e' - 'a']);
                        globalVariable.setDetected(ib.minor);//全域栓鎖
                        pointData.setPointData(ib.minor);//儲存設定
                        scanLeDevice(false);
                    }
                } else return;
            } else return;
        } else return;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(Tag, "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

    //endregion

    //region Module Method
    private void replaceFragment(Fragment fragment, int id) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        navigationView.getMenu().findItem(id).setChecked(true);
        toolbar.setTitle(navigationView.getMenu().findItem(id).getTitle());
    }


    private void scanLeDevice(final boolean enable) {
        if (enable) {
            if (!Scanning) {
                helper.startIndeterminate();
                mHandler.postDelayed(runnable, SCAN_PERIOD);
                Snackbar.make(findViewById(R.id.content_frame), "搜尋中...", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                Scanning = true;
                mBluetoothAdapter.startLeScan(MainActivity.this);
            }
        } else {
            Scanning = false;
            mBluetoothAdapter.stopLeScan(MainActivity.this);
            helper.startDeterminate();
            mHandler.removeCallbacks(runnable);
        }
    }

    //endregion


}
