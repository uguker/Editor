package com.uguke.code.editor;

import android.os.Build;
import android.webkit.WebView;

/**
 * 功能描述：
 * @author LeiJue
 * @time 2017/11/02
 */
public class Editor {

    private WebView web;

    public Editor(WebView webView) {
        this.web = webView;
    }


    // TODO 基础功能

    /** 功能描述：撤销 **/
    public void undo() {
        load("javascript:undo()");
    }

    /** 功能描述：恢复 **/
    public void redo() {
        load("javascript:redo()");
    }

    /** 功能描述：获得焦点 **/
    public void focus() {
        load("javascript:focus()");
    }

    /** 功能描述：失去焦点 **/
    public void blur() {
        load("javascript:blur()");
    }

    /** 功能描述：重置 **/
    public void reset() {
        load("javascript:reset()");
    }

    /** 功能描述：不可编辑 **/
    public void disable() {
        load("javascript:disable()");
    }

    /** 功能描述：可编辑 **/
    public void enable() {
        load("javascript:enable()");
    }

    // TODO 字体样式

    /** 功能描述：设置为粗体 **/
    public void bold() {
        load("javascript:bold()");
    }

    /** 功能描述：设置为斜体 **/
    public void italic() {
        load("javascript:italic()");
    }

    /** 功能描述：设置下划线 **/
    public void underline() {
        load("javascript:underline()");
    }

    /** 功能描述：设置删除线 **/
    public void strikethrough() {
        load("javascript:strikethrough()");
    }

    /** 功能描述：设置为上标 **/
    public void superscript() {
        load("javascript:superscript()");
    }

    /** 功能描述：设置为下标 **/
    public void subscript() {
        load("javascript:subscript()");
    }

    /** 功能描述：设置背景色 **/
    public void background(String color) {
        load("javascript:background('" + color + "')");
    }

    /** 功能描述：设置文字颜色 **/
    public void fontColor(String color) {
        load("javascript:fontColor('" + color + "')");
    }

    /** 功能描述：设置文字背景色 **/
    public void backColor(String color) {
        load("javascript:backColor('" + color + "')");
    }

    /** 功能描述：设置文字前景色 **/
    public void foreColor(String color) {
        load("javascript:foreColor('" + color + "')");
    }

    /** 功能描述：设置文字名称 **/
    public void fontName(String fontName) {
        load("javascript:fontName('" + fontName + "')");
    }

    /** 功能描述：设置文字大小 **/
    public void fontSize(double foreSize) {
        load("javascript:fontSize(" + foreSize + ")");
    }

    /** 功能描述：移除样式 **/
    public void removeFormat() { load("javascript:removeFormat()"); }

    // TODO 段落相关

    /** 功能描述：设置文字居左 **/
    public void justifyLeft() {
        load("javascript:justifyLeft()");
    }

    /** 功能描述：设置文字居右 **/
    public void justifyRight() {
        load("javascript:justifyRight()");
    }

    /** 功能描述：设置文字居中 **/
    public void justifyCenter() {
        load("javascript:justifyCenter()");
    }

    /** 功能描述：设置文字充满 **/
    public void justifyFull() {
        load("javascript:justifyFull()");
    }

    /** 功能描述：设置为有序列表 **/
    public void insertOrderedList() {
        load("javascript:insertOrderedList()");
    }

    /** 功能描述：设置为无序列表 **/
    public void insertUnorderedList() {
        load("javascript:insertUnorderedList()");
    }

    /** 功能描述：设置缩进 **/
    public void indent() {
        load("javascript:indent()");
    }

    /** 功能描述：设置不缩进 **/
    public void outdent() {
        load("javascript:outdent()");
    }

    /** 功能描述：设置为引用块 **/
    public void formatBlockquote() {
        load("javascript:formatBlock('blockquote')");
    }

    /** 功能描述：设置为代码块 **/
    public void formatBlockCode() {
        load("javascript:formatBlock('pre')");
    }

    /** 功能描述：设置为普通文字 **/
    public void formatPara() {
        load("javascript:formatPara()");
    }

    /** 功能描述：设置为标题一 **/
    public void formatH1() {
        load("javascript:formatH1()");
    }

    /** 功能描述：设置为标题二 **/
    public void formatH2() {
        load("javascript:formatH2()");
    }

    /** 功能描述：设置为标题三 **/
    public void formatH3() {
        load("javascript:formatH3()");
    }

    /** 功能描述：设置为标题四 **/
    public void formatH4() {
        load("javascript:formatH4()");
    }

    /** 功能描述：设置为标题五 **/
    public void formatH5() {
        load("javascript:formatH5()");
    }

    /** 设功能描述：设置为标题六 **/
    public void formatH6() {
        load("javascript:formatH6()");
    }

    /** 功能描述：设置行间高度 **/
    public void lineHeight(double lineHeight) {
        load("javascript:lineHeight(" + lineHeight + ")");
    }

    // TODO 插入操作

    /** 功能描述：插入图片 **/
    public void insertImageUrl(String imageUrl) {
        load("javascript:insertImageUrl('" + imageUrl + "')");
    }

    /** 功能描述：插入图片 **/
    public void insertImageData(String fileName, String base64Str) {
        String imageUrl = "data:image/" + fileName.split("\\.")[1] + ";base64," + base64Str;
        load("javascript:insertImageUrl('" + imageUrl + "')");
    }

    /**
     * 功能描述：插入文本
     * @param text
     */
    public void insertText(String text) {
        load("javascript:insertText('" + text + "')");
    }

    /**
     * 功能描述：创建超链接
     *  @param linkText 链接名
     *  @param linkUrl  链接地址
     */
    public void insertLink(String linkText, String linkUrl) {
        load("javascript:insertLink('" + linkText + "','" + linkUrl + "')");
    }

    /**
     * 功能描述：插入表格
     * @param col 行
     * @param row 列
     */
    public void insertTable(int col, int row) {
        load("javascript:insertTable('" + col + "x" + row + "')");
    }

    /** 功能描述：插入分割线 **/
    public void insertHorizontalRule() {
        load("javascript:insertHorizontalRule()");
    }

    /**
     * 功能描述：设置Html
     * @param html
     */
    public void setHtml(String html) {
        load("javascript:pasteHTML('" + html + "')");
    }

    /** 功能描述：切换编辑模式（代码模式和文本模式） **/
    public void codeView() {
        load("javascript:codeView()");
    }
    /**
     * 功能描述：调用JS代码
     * @param trigger 操作代码
     */
    private void load(String trigger) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            web.evaluateJavascript(trigger, null);
        } else {
            web.loadUrl(trigger);
        }
    }
}