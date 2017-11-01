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

public class ActionView extends AppCompatImageView {

    private ActionType mActionType;
    private Editor mRichEditor;
    private Context mContext;

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
        mActionType = ActionType.fromInteger(ta.getInteger(R.styleable.ActionView_actionType, 0));
        ta.recycle();
    }

    public ActionType getActionType() {
        return mActionType;
    }

    public void setActionType(ActionType mActionType) {
        this.mActionType = mActionType;
    }

    public Editor getRichEditorAction() {
        return mRichEditor;
    }

    public void setRichEditorAction(Editor mRichEditor) {
        this.mRichEditor = mRichEditor;
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
        //TODO RichEditorAction can not be null

        switch (mActionType) {
            case BOLD:
                mRichEditor.bold();
                break;
            case ITALIC:
                mRichEditor.italic();
                break;
            case UNDERLINE:
                mRichEditor.underline();
                break;
            case SUBSCRIPT:
                mRichEditor.subscript();
                break;
            case SUPERSCRIPT:
                mRichEditor.superscript();
                break;
            case STRIKETHROUGH:
                mRichEditor.strikethrough();
                break;
            case NORMAL:
                mRichEditor.formatPara();
                break;
            case H1:
                mRichEditor.formatH1();
                break;
            case H2:
                mRichEditor.formatH2();
                break;
            case H3:
                mRichEditor.formatH3();
                break;
            case H4:
                mRichEditor.formatH4();
                break;
            case H5:
                mRichEditor.formatH5();
                break;
            case H6:
                mRichEditor.formatH6();
                break;
            case JUSTIFY_LEFT:
                mRichEditor.justifyLeft();
                break;
            case JUSTIFY_CENTER:
                mRichEditor.justifyCenter();
                break;
            case JUSTIFY_RIGHT:
                mRichEditor.justifyRight();
                break;
            case JUSTIFY_FULL:
                mRichEditor.justifyFull();
                break;
            case ORDERED:
                mRichEditor.insertOrderedList();
                break;
            case UNORDERED:
                mRichEditor.insertUnorderedList();
                break;
            case INDENT:
                mRichEditor.indent();
                break;
            case OUTDENT:
                mRichEditor.outdent();
                break;
            case LINE:
                mRichEditor.insertHorizontalRule();
                break;
            case BLOCK_QUOTE:
                mRichEditor.formatBlockquote();
                break;
            case BLOCK_CODE:
                mRichEditor.formatBlockCode();
                break;
            case CODE_VIEW:
                mRichEditor.codeView();
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

        switch (mActionType) {
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
