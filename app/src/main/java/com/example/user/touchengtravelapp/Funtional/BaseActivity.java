package com.example.user.touchengtravelapp.Funtional;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by user on 2018/9/21.
 */

public class BaseActivity extends AppCompatActivity {

    protected void showToastL(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    protected void showToastS(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
