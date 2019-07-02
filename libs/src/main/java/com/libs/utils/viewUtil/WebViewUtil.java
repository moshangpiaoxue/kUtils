package com.libs.utils.viewUtil;

import android.os.Build;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @ author：mo
 * @ data：2018/9/29
 * @ 功能：
 */
public class WebViewUtil {
    /**
     * 设置webview
     *
     * @param mWebView
     */
    public static void setWebView(WebView mWebView) {
        WebSettings settings = mWebView.getSettings();
        //启用支持javascript
        settings.setJavaScriptEnabled(true);
        //        加载网页有时候会左右滑动，没法自适应屏幕，就加上下面的两句话
        //设定支持viewport  设置此属性，可任意比例缩放
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
//        指定编码
        settings.setDefaultTextEncodingName("utf-8");
        //设定支持缩放
//        settings.setSupportZoom(true);
        //        是否显示缩放工具，默认为false。
//        settings.setBuiltInZoomControls(true);
////////////////////////////////////////////////////////////////////////////////////////////////////////
//        不显示垂直滚动条
        mWebView.setVerticalScrollBarEnabled(false);
        //        不显示水平滚动条
        mWebView.setHorizontalScrollBarEnabled(false);
        //设置 缓存模式
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //  页面加载好以后，再放开图片
        mWebView.getSettings().setBlockNetworkImage(false);
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        mWebView.getSettings().setTextZoom(100);
        // 排版适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // 缩放比例 1
        mWebView.setInitialScale(1);
        //不加上，会显示白边
//        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setWebChromeClient(new WebChromeClient());
    }
}
