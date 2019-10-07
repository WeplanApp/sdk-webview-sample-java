package com.cumberland.sample.webview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cumberland.weplansdk.WeplanSdk;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWebView();

        if (hasLocationPermission())
            initWeplanSdk();
        else checkPermissions();
    }

    private void initWebView() {
        WebView webview = findViewById(R.id.mainWebview);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("http://www.google.com");
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void initWeplanSdk() {
        WeplanSdk.INSTANCE.withContext(this)
                .withClientId("YOUR_CLIENT_ID") // Get credentials registering in https://sdk.weplan-app.com/
                .withClientSecret("YOUR_CLIENT_SECRET")
                .enable();
    }

    private void checkPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
        }, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (hasLocationPermission())
                    initWeplanSdk();
            }

            default: super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
