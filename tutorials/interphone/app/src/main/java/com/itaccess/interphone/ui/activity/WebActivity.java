package com.itaccess.interphone.ui.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itaccess.interphone.R;
import com.itaccess.interphone.utils.RoamingUtil;
import com.itaccess.interphone.utils.StringUtil;
import com.itaccess.interphone.widget.LinkMovementMethod;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by linxi on 2019/01/18.
 */

public class WebActivity extends BaseActivity {

    public static final String TAG = "WebActivity";
    public static final String WEBVIEW_TITLE = "webview_title";

    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.webview_network_error_layout)
    RelativeLayout mNetworkErrorLayout;

    @Override
    public int getLayoutRes(){
        return R.layout.activity_web;
    }

    @Override
    public void init() {
        String title = getIntent().getStringExtra(WEBVIEW_TITLE);
        if (!StringUtil.isNull(title)) {
            setToolBarTitle(title);
        } else {
            //お知らせリンクについてのタイトル
            setToolBarTitle(getResStr(R.string.notice_webview_title));
        }
        String url = getIntent().getStringExtra(LinkMovementMethod.BUNDLE_KEY_LINK_URL);

        if (!StringUtil.isNull(url)) {
            setWebView(url);
        } else {
            setWebView("https://renta.papy.co.jp/renta/sc/frm/page/help/rentaapp.htm");
        }
        showTitleLeftIcon(true);



//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
    }

    private void setWebView(String url) {
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl(url);
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    private WebViewClient webViewClient=new WebViewClient(){
        @Override
        public void onPageFinished(WebView view, String url) {
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            hideErrorLayout();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.equals("http://www.google.com/")){

            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView webview, int errorCode, String description, String failingUrl) {
//                Logger.d(TAG, new StringBuilder()
//                        .append("onReceivedError errorCode:")
//                        .append(errorCode)
//                        .append(" failingUrl:")
//                        .append(failingUrl).toString());

                // エラー表示.
                showErrorLayout();
        }

    };

    private WebChromeClient webChromeClient=new WebChromeClient(){

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            // 受信したタイトルがNullでなければ表示.
            if (!StringUtil.isNull(title)) {
                // タイトル設定.
                //setToolBarTitle(title);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mProgressBar.setProgress(newProgress);
        }

    };

    @OnClick(R.id.txt_left)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
               startActivity(MainActivity.class);
                break;
        }
    }

    /**
     * ネットワークエラー画面を非表示にする.
     */
    private void hideErrorLayout() {
        if (!mWebView.isShown()) {
            mWebView.setVisibility(View.VISIBLE);
        }
        if (mNetworkErrorLayout.isShown()) {
            mNetworkErrorLayout.setVisibility(View.GONE);
        }
    }

    /**
     * エラーレイアウト表示.
     */
    private void showErrorLayout() {
        // エラー表示.
        if (RoamingUtil.isNetworkConnected(this)) {
            // ネットワークエラー
            transErrorLayout(true);
        } else {
            // 汎用エラー
            transErrorLayout(false);
        }
    }

    /**
     * エラー画面に画面を差替える.
     */
    private void transErrorLayout(boolean isNetworkError) {
        mWebView.stopLoading();
        mWebView.setVisibility(View.GONE);
        mNetworkErrorLayout.setVisibility(View.VISIBLE);
        TextView errorTextView = (TextView) mNetworkErrorLayout.findViewById(R.id.webview_error_text);
        // エラー文言の出し分け.
        if (!isNetworkError) {
            errorTextView.setText(getResources().getString(R.string.webview_other_error));
        } else {
            errorTextView.setText(getResources().getString(R.string.webview_netowork_error));
        }
    }

}
