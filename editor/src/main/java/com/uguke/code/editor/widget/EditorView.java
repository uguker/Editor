package com.uguke.code.editor.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;


import com.uguke.code.editor.Editor;
import com.uguke.code.editor.EditorManager;

import java.io.File;

public class EditorView extends LinearLayout {


    private String content;

    private WebView webView;

    private Editor action;



    public EditorView(Context context) {
        super(context);
        initWeb();
    }

    public EditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWeb();
    }

    public EditorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWeb();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EditorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initWeb();
    }
    // TODO 初始化WEB控件
    private void initWeb() {
        webView = new WebView(getContext());
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);
        addView(webView);
        webView.setBackgroundColor(Color.RED);
        webView.setWebChromeClient(new CustomWebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(new EditorManager() {}, "Editor");
        webView.loadUrl("file:///android_asset/editor.html");
        action = new Editor(webView);
    }

    private class CustomWebChromeClient extends WebChromeClient {
        @Override public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {

            }
        }

        @Override public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        //清空缓存
        webView.clearCache(true);
        if (Build.VERSION.SDK_INT >= 21) {
            removeView(webView);
            webView.removeAllViews();
            webView.destroy();
        } else {
            removeView(webView);
            webView.removeAllViews();
            webView.destroy();
        }
        webView = null;
        super.onDetachedFromWindow();
    }
}


