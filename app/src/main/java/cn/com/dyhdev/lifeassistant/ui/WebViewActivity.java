package cn.com.dyhdev.lifeassistant.ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.dyhdev.lifeassistant.R;

public class WebViewActivity extends BaseActivity {

    private static final String TAG = "WebViewActivity";
    @BindView(R.id.id_news_probar)
    ProgressBar mNewsProbar;
    @BindView(R.id.id_news_webview)
    WebView mNewsWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String url = intent.getStringExtra("url");
        Log.d(TAG, "initView: " + url);

        //加载网页
        loadWebView(url);
    }

    private void loadWebView(final String url) {
        //支持js
        mNewsWebview.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        mNewsWebview.getSettings().setSupportZoom(true);
        mNewsWebview.getSettings().setBuiltInZoomControls(true);
        //回调
        mNewsWebview.setWebChromeClient(new WebViewClient());
        mNewsWebview.setWebViewClient(new android.webkit.WebViewClient());
        mNewsWebview.loadUrl(url);

    }

    public class WebViewClient extends WebChromeClient{
        //进度变化的监听

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress == 100){
                mNewsProbar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

}
