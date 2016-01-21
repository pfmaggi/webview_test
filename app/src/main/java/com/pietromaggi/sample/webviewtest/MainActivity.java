package com.pietromaggi.sample.webviewtest;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView myWebView;
    Context mActivityContext;
    WebAppInterface mWebAppInterface;
    Button mBtnLoad;
    EditText mEdtUrlAddress;
    final private static String DEFAULT_URL = "http://192.168.178.32:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityContext = this;

        setContentView(R.layout.activity_main);

        mBtnLoad = (Button) findViewById(R.id.btnLoad);
        mEdtUrlAddress = (EditText) findViewById(R.id.edtUrlAddress);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        mEdtUrlAddress.setText(DEFAULT_URL);
        mBtnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mEdtUrlAddress.getText().toString();
                if (!URLUtil.isValidUrl(url)) {
                    url = DEFAULT_URL;
                    mEdtUrlAddress.setText(DEFAULT_URL);
                }
                myWebView.loadUrl(url);
            }
        });
    }
}
