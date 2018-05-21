package com.nivilive.gps.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.nivilive.gps.R;

public class PolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        WebView webView = findViewById(R.id.webview_policy);
        webView.loadUrl("file:///android_asset/policy.html");
    }
}
