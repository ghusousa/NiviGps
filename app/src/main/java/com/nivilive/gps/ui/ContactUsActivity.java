package com.nivilive.gps.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.nivilive.gps.R;

public class ContactUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        WebView webView = findViewById(R.id.webview_contactus);
        webView.loadUrl("file:///android_asset/contactus.html");
    }
}
