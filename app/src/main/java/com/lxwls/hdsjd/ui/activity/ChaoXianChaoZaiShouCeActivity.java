package com.lxwls.hdsjd.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;

public class ChaoXianChaoZaiShouCeActivity extends BackCommonActivity {

    private WebView webView;

    @Override
    protected int getContentView() {
        return R.layout.activity_chao_xian_chao_zai_shou_ce;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("货运车辆超限超载服务手册");
        webView= (WebView) findViewById(R.id.webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //设置可以访问文件
        settings.setAllowFileAccess(true);
        //设置支持缩放
        settings.setBuiltInZoomControls(false);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        webView.loadUrl("http://m.huodiwulian.com/kczj/sjsc.html");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.clearCache(true);
        webView.destroy();
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
