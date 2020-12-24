package com.example.user.touchengtravelapp.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.user.touchengtravelapp.R;

/**
 * Created by user on 2018/7/23.
 */

public class Fragment_c住宿資訊 extends Fragment {

    private View view;
    private WebView webview;
    private WebSettings webSettings;
    private String url="https://asiayo.com/zh-tw/list/tw/yilan-county/spot/lan-yang-museum/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_room, container, false);





        webview = (WebView) view.findViewById(R.id.webview);

//        webSettings = webview.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webview.setWebViewClient(new WebViewClient());
//        webview.loadUrl(url);
        return view;
    }
}
