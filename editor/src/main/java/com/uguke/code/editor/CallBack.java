package com.uguke.code.editor;

import android.util.Log;
import android.webkit.JavascriptInterface;

import java.io.File;

/**
 * 功能描述：JS回调接口
 * @author LeiJue
 * @time 2017/11/01
 */
public abstract class CallBack {

    /**
     * 功能描述：初始化
     */
    @JavascriptInterface public void onInit() {

    }
    
    /**
     * 功能描述：Enter键按下
     */
    @JavascriptInterface public void onEnter() {

    }

    /**
     * 功能描述：获得焦点
     */
    @JavascriptInterface public  void onFocus() {

    }

    /**
     * 功能描述：失去焦点
     */
    @JavascriptInterface public void onBlur() {

    }

    /**
     * 功能描述：图片上传
     * @param file
     */
    @JavascriptInterface public void onImageUpload(File file) {

    }

    /**
     * 功能描述：文本改变
     * @param html
     */
    @JavascriptInterface public void onChange(String html) {

    }
}
