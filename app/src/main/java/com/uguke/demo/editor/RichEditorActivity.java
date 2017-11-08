package com.uguke.demo.editor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.uguke.code.editor.ActionType;
import com.uguke.code.editor.Editor;
import com.uguke.code.editor.EditorCallback;
import com.uguke.demo.editor.fragment.EditHyperlinkFragment;
import com.uguke.demo.editor.fragment.EditTableFragment;
import com.uguke.demo.editor.fragment.EditorMenuFragment;
import com.uguke.demo.editor.interfaces.OnActionPerformListener;
import com.uguke.demo.editor.keyboard.KeyboardHeightObserver;
import com.uguke.demo.editor.keyboard.KeyboardHeightProvider;
import com.uguke.demo.editor.keyboard.KeyboardUtil;
import com.uguke.demo.editor.util.FileIOUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.uguke.code.editor.EditorManager;
import com.uguke.code.editor.widget.ActionView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint("SetJavaScriptEnabled")
public class RichEditorActivity extends AppCompatActivity
    implements KeyboardHeightObserver, View.OnClickListener {
    private WebView web;
    private ImageView editorKeyboard;
    private FrameLayout actionContainer;
    private LinearLayout actionBarContainer;

    /** The keyboard height provider */
    private KeyboardHeightProvider keyboardHeightProvider;
    private boolean isKeyboardShowing;
    private String htmlContent = "<p>Hello World</p>";

    private Editor mRichEditor;
    private EditorCallback mRichEditorCallback;

    private EditorMenuFragment mEditorMenuFragment;

    private List<ActionType> mActionTypeList =
        Arrays.asList(ActionType.BOLD, ActionType.ITALIC, ActionType.UNDERLINE,
            ActionType.STRIKETHROUGH, ActionType.SUBSCRIPT, ActionType.SUPERSCRIPT,
            ActionType.NORMAL, ActionType.H1, ActionType.H2, ActionType.H3, ActionType.H4,
            ActionType.H5, ActionType.H6, ActionType.INDENT, ActionType.OUTDENT,
            ActionType.JUSTIFY_LEFT, ActionType.JUSTIFY_CENTER, ActionType.JUSTIFY_RIGHT,
            ActionType.JUSTIFY_FULL, ActionType.ORDERED, ActionType.UNORDERED, ActionType.LINE,
            ActionType.BLOCK_CODE, ActionType.BLOCK_QUOTE);

    private List<Integer> mActionTypeIconList =
        Arrays.asList(R.drawable.ic_editor_format_bold, R.drawable.ic_editor_format_italic,
            R.drawable.ic_editor_format_underlined, R.drawable.ic_editor_format_strikethrough,
            R.drawable.ic_format_subscript, R.drawable.ic_format_superscript,
            R.drawable.ic_editor_format_para, R.drawable.ic_editor_format_h1, R.drawable.ic_editor_format_h2,
            R.drawable.ic_editor_format_h3, R.drawable.ic_editor_format_h4, R.drawable.ic_editor_format_h5,
            R.drawable.ic_editor_format_h6, R.drawable.ic_editor_format_indent_decrease,
            R.drawable.ic_editor_format_indent_increase, R.drawable.ic_editor_format_align_left,
            R.drawable.ic_editor_format_align_center, R.drawable.ic_editor_format_align_right,
            R.drawable.ic_editor_format_align_justify, R.drawable.ic_editor_format_list_numbered,
            R.drawable.ic_editor_format_list_bulleted, R.drawable.ic_editor_line, R.drawable.ic_editor_code_block,
            R.drawable.ic_editor_format_quote);

    private static final int REQUEST_CODE_CHOOSE = 0;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_acitvity);

        web = (WebView) findViewById(R.id.editor_container);
        editorKeyboard = (ImageView) findViewById(R.id.editor_keyboard);

        actionContainer = (FrameLayout) findViewById(R.id.editor_action_container);
        actionBarContainer = (LinearLayout) findViewById(R.id.editor_action_bar_container);

        initImageLoader();
        initView();

//        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40,
//            getResources().getDisplayMetrics());
//        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9,
//            getResources().getDisplayMetrics());
        int width = getResources().getDimensionPixelSize(R.dimen.editorImg);
        int padding = getResources().getDimensionPixelSize(R.dimen.paddingSmall);
        for (int i = 0, size = mActionTypeList.size(); i < size; i++) {
            final ActionView actionImageView = new ActionView(this);
            actionImageView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            actionImageView.setPadding(padding, padding, padding, padding);
            actionImageView.setActionType(mActionTypeList.get(i));
            actionImageView.setTag(mActionTypeList.get(i));
            actionImageView.setActivatedColor(R.color.colorAccent);
            actionImageView.setDeactivatedColor(R.color.tintColor);
            actionImageView.setEditor(mRichEditor);
            actionImageView.setBackgroundResource(R.drawable.btn_colored_material);
            actionImageView.setImageResource(mActionTypeIconList.get(i));
            actionImageView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    actionImageView.command();
                }
            });
            actionBarContainer.addView(actionImageView);
        }

        mEditorMenuFragment = new EditorMenuFragment();
        mEditorMenuFragment.setActionClickListener(new MOnActionPerformListener(mRichEditor));
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
            .add(R.id.editor_action_container, mEditorMenuFragment, EditorMenuFragment.class.getName())
            .commit();
    }

    /**
     * ImageLoader for insert Image
     */
    private void initImageLoader() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(false);
        imagePicker.setSaveRectangle(true);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setFocusWidth(800);
        imagePicker.setFocusHeight(800);
        imagePicker.setOutPutX(256);
        imagePicker.setOutPutY(256);
    }

    private void initView() {
        web.setWebViewClient(new WebViewClient() {
            @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        web.setWebChromeClient(new CustomWebChromeClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        mRichEditorCallback = new MRichEditorCallback();
        web.addJavascriptInterface(new EditorManager() {
            @Override
            public void notifyFontStyleChange(ActionType type, String value) {
                ActionView actionImageView =
                        (ActionView) actionBarContainer.findViewWithTag(type);
                if (actionImageView != null) {
                    actionImageView.notifyFontStyleChange(type, value);
                }
                if (mEditorMenuFragment != null) {
                    mEditorMenuFragment.updateActionStates(type, value);
                }

            }
        }, "Editor");
        web.loadUrl("file:///android_asset/editor.html");
        mRichEditor = new Editor(web);

        keyboardHeightProvider = new KeyboardHeightProvider(this);
        findViewById(R.id.fl_container).post(new Runnable() {
            @Override public void run() {
                keyboardHeightProvider.start();
            }
        });

        findViewById(R.id.editor_action).setOnClickListener(this);
        findViewById(R.id.editor_keyboard).setOnClickListener(this);
        findViewById(R.id.editor_line_height).setOnClickListener(this);
        findViewById(R.id.editor_insert_image).setOnClickListener(this);
        findViewById(R.id.editor_insert_link).setOnClickListener(this);
        findViewById(R.id.editor_insert_table).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.editor_action:
                if (actionContainer.getVisibility() == View.VISIBLE) {
                    actionContainer.setVisibility(View.GONE);
                } else {
                    if (isKeyboardShowing) {
                        KeyboardUtil.hideSoftInput(RichEditorActivity.this);
                    }
                    actionContainer.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.editor_keyboard:
                if (KeyboardUtil.isSoftShowing(this)) {
                    KeyboardUtil.hideSoftInput(this);
                    editorKeyboard.setImageResource(R.drawable.ic_editor_keyboard_show);
                } else {
                    KeyboardUtil.showSoftInput(this);
                    editorKeyboard.setImageResource(R.drawable.ic_editor_keyboard_hide);
                }
                break;
            case R.id.editor_line_height:
                mRichEditor.lineHeight(20);
                break;
            case R.id.editor_insert_image:
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CHOOSE);
                break;
            case R.id.editor_insert_link:
                insertLink();
                break;
            case R.id.editor_insert_table:
                insertTable();
                break;
            default:
                break;
        }
    }

    private void insertLink() {
        KeyboardUtil.hideSoftInput(RichEditorActivity.this);
        EditHyperlinkFragment fragment = new EditHyperlinkFragment();
        fragment.setOnHyperlinkListener(new EditHyperlinkFragment.OnHyperlinkListener() {
            @Override public void onHyperlinkOK(String address, String text) {
                mRichEditor.insertLink(text, address);
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, fragment, EditHyperlinkFragment.class.getName())
                .commit();
    }

    private void insertTable() {
        KeyboardUtil.hideSoftInput(RichEditorActivity.this);
        EditTableFragment fragment = new EditTableFragment();
        fragment.setOnTableListener(new EditTableFragment.OnTableListener() {
            @Override public void onTableOK(int rows, int cols) {
                mRichEditor.insertTable(rows, cols);
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, fragment, EditHyperlinkFragment.class.getName())
                .commit();
    }


    private class CustomWebChromeClient extends WebChromeClient {
        @Override public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                if (!TextUtils.isEmpty(htmlContent)) {
                }
                //KeyboardUtil.showSoftInput(RichEditorActivity.this);
                mRichEditor.background("gray");
                mRichEditor.fontColor("white");
                mRichEditor.removeFormat();
            }
        }

        @Override public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS
            && data != null
            && requestCode == REQUEST_CODE_CHOOSE) {
            ArrayList<ImageItem> images =
                (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null && !images.isEmpty()) {

                //1.Insert the Base64 String (Base64.NO_WRAP)
                ImageItem imageItem = images.get(0);
                mRichEditor.insertImageData(imageItem.name,
                    encodeFileToBase64Binary(imageItem.path));

                //2.Insert the ImageUrl
                //mRichEditorAction.insertImageUrl(
                //    "https://avatars0.githubusercontent.com/u/5581118?v=4&u=b7ea903e397678b3675e2a15b0b6d0944f6f129e&s=400");
            }
        }
    }

    private static String encodeFileToBase64Binary(String filePath) {
        byte[] bytes = FileIOUtil.readFile2BytesByStream(filePath);
        byte[] encoded = Base64.encode(bytes, Base64.NO_WRAP);
        return new String(encoded);
    }



    @Override public void onResume() {
        super.onResume();
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    @Override public void onPause() {
        super.onPause();
        keyboardHeightProvider.setKeyboardHeightObserver(null);
        if (actionContainer.getVisibility() == View.INVISIBLE) {
            actionContainer.setVisibility(View.GONE);
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
        keyboardHeightProvider.close();


    }

    @Override public void onKeyboardHeightChanged(int height, int orientation) {
        isKeyboardShowing = height > 0;
        if (height != 0) {
            actionContainer.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams params = actionContainer.getLayoutParams();
            params.height = height;
            actionContainer.setLayoutParams(params);
        } else if (actionContainer.getVisibility() != View.VISIBLE) {
            actionContainer.setVisibility(View.GONE);
        }
    }

    class MRichEditorCallback extends EditorCallback {

        @Override public void notifyFontStyleChange(ActionType type, final String value) {
            ActionView actionImageView =
                (ActionView) actionBarContainer.findViewWithTag(type);
            if (actionImageView != null) {
                actionImageView.notifyFontStyleChange(type, value);
            }

            if (mEditorMenuFragment != null) {
                mEditorMenuFragment.updateActionStates(type, value);
            }
        }
    }

    public class MOnActionPerformListener implements OnActionPerformListener {
        private Editor mRichEditor;

        public MOnActionPerformListener(Editor mRichEditor) {
            this.mRichEditor = mRichEditor;
        }

        @Override public void onActionPerform(ActionType type, Object... values) {
            if (mRichEditor == null) {
                return;
            }

            String value = null;
            if (values != null && values.length > 0) {
                value = (String) values[0];
            }

            switch (type) {
                case SIZE:
                    mRichEditor.fontSize(Double.valueOf(value));
                    break;
                case LINE_HEIGHT:
                    mRichEditor.lineHeight(Double.valueOf(value));
                    break;
                case FORE_COLOR:
                    mRichEditor.foreColor(value);
                    break;
                case BACK_COLOR:
                    mRichEditor.backColor(value);
                    break;
                case FAMILY:
                    mRichEditor.fontName(value);
                    break;
                case IMAGE:
                    //onClickInsertImage();
                    break;
                case LINK:
                    insertLink();
                    break;
                case TABLE:
                    insertTable();
                    break;
                case BOLD:
                case ITALIC:
                case UNDERLINE:
                case SUBSCRIPT:
                case SUPERSCRIPT:
                case STRIKETHROUGH:
                case JUSTIFY_LEFT:
                case JUSTIFY_CENTER:
                case JUSTIFY_RIGHT:
                case JUSTIFY_FULL:
                case ORDERED:
                case UNORDERED:
                case INDENT:
                case OUTDENT:
                case BLOCK_QUOTE:
                case BLOCK_CODE:
                case NORMAL:
                case H1:
                case H2:
                case H3:
                case H4:
                case H5:
                case H6:
                case LINE:
                    ActionView actionImageView =
                        (ActionView) actionBarContainer.findViewWithTag(type);
                    if (actionImageView != null) {
                        actionImageView.performClick();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
