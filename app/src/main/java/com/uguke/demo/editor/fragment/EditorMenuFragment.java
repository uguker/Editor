package com.uguke.demo.editor.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.uguke.code.editor.ActionType;
import com.uguke.demo.editor.R;
import com.uguke.demo.editor.interfaces.OnActionPerformListener;
import com.uguke.demo.editor.widget.ColorPaletteView;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EditorMenuFragment extends Fragment implements View.OnClickListener {
    private View contentView;
    private TextView fontSizeText;
    private TextView fontName;
    private TextView fontSpace;
    private ColorPaletteView fontTextColor;
    private ColorPaletteView highlightColor;

    private OnActionPerformListener mActionClickListener;

    private int [] ids = {
            // TODO 选择功能
            R.id.editor_font_name, R.id.editor_font_size, R.id.editor_line_height,
            // TODO 单项功能
            R.id.editor_bold, R.id.editor_italic, R.id.editor_underline,
            R.id.editor_strikethrough, R.id.editor_justify_left, R.id.editor_justify_center,
            R.id.editor_justify_right, R.id.editor_justify_full, R.id.editor_subscript,
            R.id.editor_superscript, R.id.editor_insert_numbers, R.id.editor_insert_bullets,
            R.id.editor_indent, R.id.editor_outdent, R.id.editor_code_view,
            R.id.editor_blockquote, R.id.editor_code_block, R.id.editor_normal, R.id.editor_h1,
            R.id.editor_h2, R.id.editor_h3, R.id.editor_h4, R.id.editor_h5,  R.id.editor_insert_image,
            R.id.editor_insert_link, R.id.editor_table, R.id.editor_line
    };

    private Map<Integer, ActionType> mViewTypeMap = new HashMap<Integer, ActionType>() {
        {
            put(R.id.editor_bold, ActionType.BOLD);
            put(R.id.editor_italic, ActionType.ITALIC);
            put(R.id.editor_underline, ActionType.UNDERLINE);
            put(R.id.editor_strikethrough, ActionType.STRIKETHROUGH);
            put(R.id.editor_justify_left, ActionType.JUSTIFY_LEFT);
            put(R.id.editor_justify_center, ActionType.JUSTIFY_CENTER);
            put(R.id.editor_justify_right, ActionType.JUSTIFY_RIGHT);
            put(R.id.editor_justify_full, ActionType.JUSTIFY_FULL);
            put(R.id.editor_subscript, ActionType.SUBSCRIPT);
            put(R.id.editor_superscript, ActionType.SUPERSCRIPT);
            put(R.id.editor_insert_numbers, ActionType.ORDERED);
            put(R.id.editor_insert_bullets, ActionType.UNORDERED);
            put(R.id.editor_indent, ActionType.INDENT);
            put(R.id.editor_outdent, ActionType.OUTDENT);
            put(R.id.editor_blockquote, ActionType.BLOCK_QUOTE);
            put(R.id.editor_code_block, ActionType.BLOCK_CODE);
            put(R.id.editor_normal, ActionType.NORMAL);
            put(R.id.editor_h1, ActionType.H1);
            put(R.id.editor_h2, ActionType.H2);
            put(R.id.editor_h3, ActionType.H3);
            put(R.id.editor_h4, ActionType.H4);
            put(R.id.editor_h5, ActionType.H5);
            put(R.id.editor_insert_image, ActionType.IMAGE);
            put(R.id.editor_insert_link, ActionType.LINK);
            put(R.id.editor_table, ActionType.TABLE);
            put(R.id.editor_line, ActionType.LINE);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.editor_menu_fragment, null);
        ButterKnife.bind(this, contentView);
        initView();
        return contentView;
    }

    private void initView() {
        
        fontSizeText = (TextView) contentView.findViewById(R.id.editor_font_size_text);
        fontName = (TextView) contentView.findViewById(R.id.editor_font_name);
        fontSpace = (TextView) contentView.findViewById(R.id.editor_font_space);
        fontTextColor = (ColorPaletteView) contentView.findViewById(R.id.editor_font_text_color);
        highlightColor = (ColorPaletteView) contentView.findViewById(R.id.editor_highlight_color);

        fontTextColor.setOnColorChangeListener(new ColorPaletteView.OnColorChangeListener() {
            @Override public void onColorChange(String color) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionPerform(ActionType.FORE_COLOR, color);
                }
            }
        });

        highlightColor.setOnColorChangeListener(new ColorPaletteView.OnColorChangeListener() {
            @Override public void onColorChange(String color) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionPerform(ActionType.BACK_COLOR, color);
                }
            }
        });

        for (int id : ids) {
            contentView.findViewById(id).setOnClickListener(this);
        }
    }


    private void openFontSettingFragment(final int type) {
        FontSettingFragment fontSettingFragment = new FontSettingFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(FontSettingFragment.TYPE, type);
        fontSettingFragment.setArguments(bundle);

        fontSettingFragment.setOnResultListener(new FontSettingFragment.OnResultListener() {
            @Override public void onResult(String result) {
                if (mActionClickListener != null) {
                    switch (type) {
                        case FontSettingFragment.TYPE_SIZE:
                            fontSizeText.setText(result);
                            mActionClickListener.onActionPerform(ActionType.SIZE, result);
                            break;
                        case FontSettingFragment.TYPE_LINE_HGEIGHT:
                            fontSpace.setText(result);
                            mActionClickListener.onActionPerform(ActionType.LINE_HEIGHT, result);
                            break;
                        case FontSettingFragment.TYPE_FONT_FAMILY:
                            fontName.setText(result);
                            mActionClickListener.onActionPerform(ActionType.FAMILY, result);
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
            .add(R.id.editor_action_container, fontSettingFragment, FontSettingFragment.class.getName())
            .hide(EditorMenuFragment.this)
            .commit();
    }

    public void updateActionStates(final ActionType type, final boolean isActive) {
        contentView.post(new Runnable() {
            @Override public void run() {
                View view = null;
                for (Map.Entry<Integer, ActionType> e : mViewTypeMap.entrySet()) {
                    Integer key = e.getKey();
                    if (e.getValue() == type) {
                        view = contentView.findViewById(key);
                        break;
                    }
                }

                if (view == null) {
                    return;
                }

                switch (type) {
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
                        if (isActive) {
                            ((ImageView) view).setColorFilter(
                                ContextCompat.getColor(getContext(), R.color.colorAccent));
                        } else {
                            ((ImageView) view).setColorFilter(
                                ContextCompat.getColor(getContext(), R.color.tintColor));
                        }
                        break;
                    case NORMAL:
                    case H1:
                    case H2:
                    case H3:
                    case H4:
                    case H5:
                    case H6:
                        if (isActive) {
                            view.setBackgroundResource(R.drawable.round_rectangle_blue);
                        } else {
                            view.setBackgroundResource(R.drawable.round_rectangle_white);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void setActionClickListener(OnActionPerformListener mActionClickListener) {
        this.mActionClickListener = mActionClickListener;
    }

    public void updateActionStates(ActionType type, final String value) {
        switch (type) {
            case FAMILY:
                updateFontFamilyStates(value);
                break;
            case SIZE:
                updateFontStates(ActionType.SIZE, Double.valueOf(value));
                break;
            case FORE_COLOR:
            case BACK_COLOR:
                updateFontColorStates(type, value);
                break;
            case LINE_HEIGHT:
                updateFontStates(ActionType.LINE_HEIGHT, Double.valueOf(value));
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
            case NORMAL:
            case H1:
            case H2:
            case H3:
            case H4:
            case H5:
            case H6:
            case ORDERED:
            case UNORDERED:
                updateActionStates(type, Boolean.valueOf(value));
                break;
            default:
                break;
        }
    }

    private void updateFontFamilyStates(final String font) {
        contentView.post(new Runnable() {
            @Override public void run() {
                fontName.setText(font);
            }
        });
    }

    private void updateFontStates(final ActionType type, final double value) {
        contentView.post(new Runnable() {
            @Override public void run() {
                switch (type) {
                    case SIZE:
                        fontSizeText.setText(String.valueOf((int) value));
                        break;
                    case LINE_HEIGHT:
                        fontSpace.setText(String.valueOf(value));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void updateFontColorStates(final ActionType type, final String color) {
        contentView.post(new Runnable() {
            @Override public void run() {
                String selectedColor = rgbToHex(color);
                if (selectedColor != null) {
                    if (type == ActionType.FORE_COLOR) {
                        fontTextColor.setSelectedColor(selectedColor);
                    } else if (type == ActionType.BACK_COLOR) {
                        highlightColor.setSelectedColor(selectedColor);
                    }
                }
            }
        });
    }

    public static String rgbToHex(String rgb) {
        Pattern c = Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)");
        Matcher m = c.matcher(rgb);
        if (m.matches()) {
            return String.format("#%02x%02x%02x", Integer.valueOf(m.group(1)),
                Integer.valueOf(m.group(2)), Integer.valueOf(m.group(3)));
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.editor_font_size:
                openFontSettingFragment(FontSettingFragment.TYPE_SIZE);
                break;
            case R.id.editor_highlight_color:
                openFontSettingFragment(FontSettingFragment.TYPE_LINE_HGEIGHT);
                break;
            case R.id.editor_font_name:
                openFontSettingFragment(FontSettingFragment.TYPE_FONT_FAMILY);
                break;
            default:
                if (mActionClickListener == null) {
                    return;
                }
                ActionType type = mViewTypeMap.get(v.getId());
                mActionClickListener.onActionPerform(type);
                break;
        }
    }
}
