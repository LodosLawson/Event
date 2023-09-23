package com.macsoftware.event;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebExmp extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_exmp);

        webView = findViewById(R.id.webexmp);
        webView.getSettings().setJavaScriptEnabled(true);

        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);

        // WebView'deki sayfa yüklendiğinde geri dönmek için WebViewClient kullanın
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // WebView'deki bağlantıyı işleyin ve geri dönün
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Geri tuşuna basıldığında, aktiviteyi sonlandırın ve önceki aktiviteye geri dönün
        finish();
    }
}