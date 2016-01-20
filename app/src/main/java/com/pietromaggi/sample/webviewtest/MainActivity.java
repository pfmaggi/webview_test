package com.pietromaggi.sample.webviewtest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView myWebView;
    Context mActivityContext;
    WebAppInterface mWebAppInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityContext = this;

        setContentView(R.layout.activity_main);

        myWebView = (WebView) findViewById(R.id.webview);
        mWebAppInterface = new WebAppInterface(mActivityContext);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(final WebView view, String finishUrl) {
                super.onPageFinished(view, finishUrl);
                // android 4.4 may lost value of 'JSInterface' when operating iframe
                view.addJavascriptInterface(mWebAppInterface, "JSInterface");
            }
        });
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(true);
        myWebView.addJavascriptInterface(mWebAppInterface, "JSInterface");
        myWebView.loadUrl("http://192.168.178.32:8080/");
    }
}
