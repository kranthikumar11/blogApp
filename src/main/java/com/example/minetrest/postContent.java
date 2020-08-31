package com.example.minetrest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class postContent extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_content);
        webView=findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);

        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                String javaScript = "javascript:(()=>" +
                        "{var head=document.getElementsByTagName(\"header\");" +
                        "head[0].innerText=\"minetrest.blogspot.com\";"+
                        "head[0].style.color=\"#03DAC5\";"+
                        "head[0].style.paddingLeft=\"30px\";"+
                        "head[0].style.paddingTop=\"30px\";"+
                        "var pop = document.getElementsByClassName(\"widget PopularPosts\");" +
                        "pop[0].hidden = true;" +
                        "var com=document.getElementsByClassName(\"comments embed\");" +
                        "com[0].hidden=true;" +
                        "var foot=document.getElementsByTagName(\"footer\");" +
                        "foot[0].hidden=true;" +
                        "var date=document.getElementsByClassName(\"post-header\");" +
                        "date[0].hidden=true;" +
                        "var share=document.getElementsByClassName(\"post-footer\");" +
                        "share[0].hidden=true;})()";
                webView.loadUrl(javaScript);
            }
        });

        webView.loadUrl(getIntent().getStringExtra("url"));
    }
    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }
}