package com.uguke.code.editor;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import java.util.Arrays;
import java.util.List;

/**
 * 功能描述：JS回调接口
 * @author LeiJue
 * @time 2017/11/01
 */
public class EditorManager {

    private String html;
    private FontStyle mFontStyle = new FontStyle();

    private List<ActionType> fonts = Arrays.asList(
            ActionType.NORMAL,
            ActionType.H1,
            ActionType.H2,
            ActionType.H3,
            ActionType.H4,
            ActionType.H5,
            ActionType.H6);

    private List<ActionType> mTextAlignGroup = Arrays.asList(
            ActionType.JUSTIFY_LEFT,
            ActionType.JUSTIFY_CENTER,
            ActionType.JUSTIFY_RIGHT,
            ActionType.JUSTIFY_FULL);

    private List<ActionType> mListStyleGroup = Arrays.asList(
            ActionType.ORDERED,
            ActionType.UNORDERED);

    private void updateStyle(FontStyle style) {
        if (mFontStyle.getFontFamily() == null || !mFontStyle.getFontFamily()
                .equals(style.getFontFamily())) {
            if (!TextUtils.isEmpty(style.getFontFamily())) {
                String font = style.getFontFamily().split(",")[0].replace("\"", "");
                notifyFontStyleChange(ActionType.FAMILY, font);
            }
        }

        if (mFontStyle.getFontForeColor() == null || !mFontStyle.getFontForeColor()
                .equals(style.getFontForeColor())) {
            if (!TextUtils.isEmpty(style.getFontForeColor())) {
                notifyFontStyleChange(ActionType.FORE_COLOR, style.getFontForeColor());
            }
        }

        if (mFontStyle.getFontBackColor() == null || !mFontStyle.getFontBackColor()
                .equals(style.getFontBackColor())) {
            if (!TextUtils.isEmpty(style.getFontBackColor())) {
                notifyFontStyleChange(ActionType.BACK_COLOR, style.getFontBackColor());
            }
        }

        if (mFontStyle.getFontSize() != style.getFontSize()) {
            notifyFontStyleChange(ActionType.SIZE, String.valueOf(style.getFontSize()));
        }

        if (mFontStyle.getTextAlign() != style.getTextAlign()) {
            for (int i = 0, size = mTextAlignGroup.size(); i < size; i++) {
                ActionType type = mTextAlignGroup.get(i);
                notifyFontStyleChange(type, String.valueOf(type == style.getTextAlign()));
            }
        }

        if (mFontStyle.getLineHeight() != style.getLineHeight()) {
            notifyFontStyleChange(ActionType.LINE_HEIGHT,
                    String.valueOf(style.getLineHeight()));
        }

        if (mFontStyle.isBold() != style.isBold()) {
            notifyFontStyleChange(ActionType.BOLD, String.valueOf(style.isBold()));
        }

        if (mFontStyle.isItalic() != style.isItalic()) {
            notifyFontStyleChange(ActionType.ITALIC, String.valueOf(style.isItalic()));
        }

        if (mFontStyle.isUnderline() != style.isUnderline()) {
            notifyFontStyleChange(ActionType.UNDERLINE, String.valueOf(style.isUnderline()));
        }

        if (mFontStyle.isSubscript() != style.isSubscript()) {
            notifyFontStyleChange(ActionType.SUBSCRIPT, String.valueOf(style.isSubscript()));
        }

        if (mFontStyle.isSuperscript() != style.isSuperscript()) {
            notifyFontStyleChange(ActionType.SUPERSCRIPT,
                    String.valueOf(style.isSuperscript()));
        }

        if (mFontStyle.isStrikethrough() != style.isStrikethrough()) {
            notifyFontStyleChange(ActionType.STRIKETHROUGH,
                    String.valueOf(style.isStrikethrough()));
        }

        if (mFontStyle.getFontBlock() != style.getFontBlock()) {
            for (int i = 0, size = fonts.size(); i < size; i++) {
                ActionType type = fonts.get(i);
                notifyFontStyleChange(type, String.valueOf(type == style.getFontBlock()));
            }
        }

        if (mFontStyle.getListStyle() != style.getListStyle()) {
            for (int i = 0, size = mListStyleGroup.size(); i < size; i++) {
                ActionType type = mListStyleGroup.get(i);
                notifyFontStyleChange(type, String.valueOf(type == style.getListStyle()));
            }
        }

        mFontStyle = style;
    }

    public void notifyFontStyleChange(ActionType type, String value) {

    }

    /**
     * 功能描述：获取Html内容
     * @return 
     */
    public String getHtml() {
        return html;
    }

    // TODO JS交互接口

    /** 功能描述：初始化 **/
    @JavascriptInterface public void onInit() {

    }
    
    /** 功能描述：Enter键按下 **/
    @JavascriptInterface public void onEnter() {}

    /** 功能描述：获得焦点 **/
    @JavascriptInterface public  void onFocus() {

    }

    /** 功能描述：失去焦点 **/
    @JavascriptInterface public void onBlur() {

    }

    /** 功能描述：文本改变 **/
    @JavascriptInterface public void onChange(String html) {
        this.html = html;
        Log.e("数据", html);
    }
}
