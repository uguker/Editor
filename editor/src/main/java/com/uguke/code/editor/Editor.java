package com.uguke.code.editor;

import android.os.Build;
import android.webkit.WebView;

public class Editor {

    private WebView mWebView;

    public Editor(WebView webView) {
        this.mWebView = webView;
    }
    /** 撤销 **/
    public void undo() {
        load("javascript:undo()");
    }
    /** 恢复 **/
    public void redo() {
        load("javascript:redo()");
    }
    /** 设置焦点 **/
    public void focus() {
        load("javascript:focus()");
    }
    /** 重置 **/
    public void reset() {
        load("javascript:reset()");
    }
    /** 不可编辑 **/
    public void disable() {
        load("javascript:disable()");
    }
    /** 可编辑 **/
    public void enable() {
        load("javascript:enable()");
    }
    // TODO 字体相关
    /** 粗体 **/
    public void bold() {
        load("javascript:bold()");
    }
    /** 斜体 **/
    public void italic() {
        load("javascript:italic()");
    }
    /** 下划线 **/
    public void underline() {
        load("javascript:underline()");
    }
    /** 删除线 **/
    public void strikethrough() {
        load("javascript:strikethrough()");
    }
    /** 上标 **/
    public void superscript() {
        load("javascript:superscript()");
    }
    /** 下标 **/
    public void subscript() {
        load("javascript:subscript()");
    }
    /** 文字背景色 **/
    public void backColor(String color) {
        load("javascript:backColor('" + color + "')");
    }
    /** 文字前景色 **/
    public void foreColor(String color) {
        load("javascript:foreColor('" + color + "')");
    }
    /** 文字名称 **/
    public void fontName(String fontName) {
        load("javascript:fontName('" + fontName + "')");
    }
    /** 文字大小 **/
    public void fontSize(double foreSize) {
        load("javascript:fontSize(" + foreSize + ")");
    }
    // TODO 段落相关
    public void justifyLeft() {
        load("javascript:justifyLeft()");
    }

    public void justifyRight() {
        load("javascript:justifyRight()");
    }

    public void justifyCenter() {
        load("javascript:justifyCenter()");
    }

    public void justifyFull() {
        load("javascript:justifyFull()");
    }

    public void insertOrderedList() {
        load("javascript:insertOrderedList()");
    }

    public void insertUnorderedList() {
        load("javascript:insertUnorderedList()");
    }

    public void indent() {
        load("javascript:indent()");
    }

    public void outdent() {
        load("javascript:outdent()");
    }

    public void formatPara() {
        load("javascript:formatPara()");
    }

    public void formatH1() {
        load("javascript:formatH1()");
    }

    public void formatH2() {
        load("javascript:formatH2()");
    }

    public void formatH3() {
        load("javascript:formatH3()");
    }

    public void formatH4() {
        load("javascript:formatH4()");
    }

    public void formatH5() {
        load("javascript:formatH5()");
    }

    public void formatH6() {
        load("javascript:formatH6()");
    }

    public void lineHeight(double lineHeight) {
        load("javascript:lineHeight(" + lineHeight + ")");
    }

    public void insertImageUrl(String imageUrl) {
        load("javascript:insertImageUrl('" + imageUrl + "')");
    }

    public void insertImageData(String fileName, String base64Str) {
        String imageUrl = "data:image/" + fileName.split("\\.")[1] + ";base64," + base64Str;
        load("javascript:insertImageUrl('" + imageUrl + "')");
    }

    public void insertText(String text) {
        load("javascript:insertText('" + text + "')");
    }

    public void createLink(String linkText, String linkUrl) {
        load("javascript:createLink('" + linkText + "','" + linkUrl + "')");
    }

    public void unlink() {
        load("javascript:unlink()");
    }

    public void codeView() {
        load("javascript:codeView()");
    }

    public void insertTable(int col, int row) {
        load("javascript:insertTable('" + col + "x" + row + "')");
    }

    public void insertHorizontalRule() {
        load("javascript:insertHorizontalRule()");
    }

    public void formatBlockquote() {
        load("javascript:formatBlock('blockquote')");
    }

    public void formatBlockCode() {
        load("javascript:formatBlock('pre')");
    }

    public void insertHtml(String html) {
        load("javascript:pasteHTML('" + html + "')");
    }

    /**
     * 功能描述：调用JS代码
     * @param trigger 操作代码
     */
    private void load(String trigger) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.mWebView.evaluateJavascript(trigger, null);
        } else {
            this.mWebView.loadUrl(trigger);
        }
    }
}