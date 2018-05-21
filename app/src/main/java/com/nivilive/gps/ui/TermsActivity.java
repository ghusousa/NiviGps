package com.nivilive.gps.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.nivilive.gps.R;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        WebView webView = findViewById(R.id.webview_terms);
  //      webView.loadUrl("file:///android_asset/terms.html");
        WebSettings ws = webView.getSettings();
  //      ws.setJavaScriptEnabled(true);
        webView.loadUrl("http://nivilive.com/index.html");

    }
}
