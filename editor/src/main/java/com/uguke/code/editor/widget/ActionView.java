package com.uguke.code.editor.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.even.editor.R;
import com.uguke.code.editor.ActionType;
import com.uguke.code.editor.Editor;

/**
 * 功能描述：操作按钮
 * @author LeiJue
 * @time 2017/11/02
 */
public class ActionView extends AppCompatImageView {


    private Editor editor;
    private Context mContext;
    private ActionType actionType;

    private boolean enabled = true;
    private boolean activated = true;

    private int enabledColor;
    private int disabledColor;
    private int activatedColor;
    private int deactivatedColor;

    public ActionView(Context context) {
        this(context, null);
    }

    public ActionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ActionView);
        actionType = ActionType.fromInteger(ta.getInteger(R.styleable.ActionView_actionType, 0));
        ta.recycle();
    }
    
    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isActivated() {
        return activated;
    }

    public void command() {
        switch (actionType) {
            case BOLD:              //粗体
                editor.bold();
                break;
            case ITALIC:            //斜体
                editor.italic();
                break;
            case UNDERLINE:         //下划线
                editor.underline();
                break;
            case SUBSCRIPT:         //上标
                editor.subscript();
                break;
            case SUPERSCRIPT:       //下标
                editor.superscript();
                break;
            case STRIKETHROUGH:     //删除线
                editor.strikethrough();
                break;
            case NORMAL:            //正常
                editor.formatPara();
                break;
            case H1:                //标题一
                editor.formatH1();
                break;
            case H2:                //标题二
                editor.formatH2();
                break;
            case H3:                //标题三
                editor.formatH3();
                break;
            case H4:                //标题四
                editor.formatH4();
                break;
            case H5:                //标题五
                editor.formatH5();
                break;
            case H6:                //标题六
                editor.formatH6();
                break;
            case JUSTIFY_LEFT:      //文本居左
                editor.justifyLeft();
                break;
            case JUSTIFY_CENTER:    //文本居中
                editor.justifyCenter();
                break;
            case JUSTIFY_RIGHT:     //文本居右
                editor.justifyRight();
                break;
            case JUSTIFY_FULL:      //文本充满
                editor.justifyFull();
                break;
            case ORDERED:           //有序列表
                editor.insertOrderedList();
                break;
            case UNORDERED:         //无序列表
                editor.insertUnorderedList();
                break;
            case INDENT:            //缩进
                editor.indent();
                break;
            case OUTDENT:           //顶格
                editor.outdent();
                break;
            case LINE:              //分割线
                editor.insertHorizontalRule();
                break;
            case BLOCK_QUOTE:       //引用块
                editor.formatBlockquote();
                break;
            case BLOCK_CODE:        //代码块
                editor.formatBlockCode();
                break;
            case CODE_VIEW:         //编辑模式
                editor.codeView();
                break;
        }
    }

    public void command(String value) {

        //case FAMILY:
        //    mEditorMenuFragment.updateFontFamilyStates(value);
        //    break;
        //case SIZE:
        //    mEditorMenuFragment.updateFontStates(ActionType.SIZE, Double.valueOf(value));
        //    break;
        //case FORE_COLOR:
        //case BACK_COLOR:
        //    mEditorMenuFragment.updateFontColorStates(type, value);
        //    break;
        //case LINE_HEIGHT:
        //    mEditorMenuFragment.updateFontStates(ActionType.LINE_HEIGHT, Double.valueOf(value));
        //    break;

        switch (actionType) {
            case FAMILY:
                break;
            case SIZE:
                break;
            case LINE_HEIGHT:
                break;
            case FORE_COLOR:
                break;
            case BACK_COLOR:
                break;
            case IMAGE:
                break;
            case LINK:
                break;
            case TABLE:
                break;
        }
    }

    public void resetStatus() {
    }

    public int getEnabledColor() {
        return enabledColor;
    }

    public void setEnabledColor(int enabledColor) {
        this.enabledColor = enabledColor;
    }

    public int getDisabledColor() {
        return disabledColor;
    }

    public void setDisabledColor(int disabledColor) {
        this.disabledColor = disabledColor;
    }

    public int getActivatedColor() {
        return activatedColor;
    }

    public void setActivatedColor(int activatedColor) {
        this.activatedColor = activatedColor;
    }

    public int getDeactivatedColor() {
        return deactivatedColor;
    }

    public void setDeactivatedColor(int deactivatedColor) {
        this.deactivatedColor = deactivatedColor;
    }

    public void notifyFontStyleChange(final ActionType type, final String value) {
        post(new Runnable() {
            @Override public void run() {
                switch (type) {
                    case BOLD:
                    case ITALIC:
                    case UNDERLINE:
                    case SUBSCRIPT:
                    case SUPERSCRIPT:
                    case STRIKETHROUGH:
                    case NORMAL:
                    case H1:
                    case H2:
                    case H3:
                    case H4:
                    case H5:
                    case H6:
                    case JUSTIFY_LEFT:
                    case JUSTIFY_CENTER:
                    case JUSTIFY_RIGHT:
                    case JUSTIFY_FULL:
                    case ORDERED:
                    case UNORDERED:
                        setColorFilter(ContextCompat.getColor(mContext,
                            Boolean.valueOf(value) ? getActivatedColor() : getDeactivatedColor()));
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
