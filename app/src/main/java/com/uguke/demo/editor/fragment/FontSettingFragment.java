package com.uguke.demo.editor.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.uguke.demo.editor.R;
import com.uguke.demo.editor.adapter.FontSettingAdapter;
import java.util.Arrays;
import java.util.List;

/**
 * 功能描述：字体属性设置
 * @author LeiJue
 * @time 2017/11/02
 */
public class FontSettingFragment extends Fragment {
    public static final String TYPE = "type";

    public static final int TYPE_SIZE = 0;
    public static final int TYPE_LINE_HGEIGHT = 1;
    public static final int TYPE_FONT_FAMILY = 2;

    /** 字体列表 **/
    private List<String> fontFamily =
        Arrays.asList("Arial", "Arial Black", "Comic Sans MS", "Courier New", "Helvetica Neue",
            "Helvetica", "Impact", "Lucida Grande", "Tahoma", "Times New Roman", "Verdana");

    /** 字体大小列表 **/
    private List<String> fontSize = Arrays.asList(
            "12", "14", "16", "18", "20",
            "22", "24", "26", "28", "36");

    /** 行间间距列表 **/
    private List<String> lineHeight = Arrays.asList(
            "1.0", "1.2", "1.4", "1.6", "1.8", "2.0", "3.0");
    
    private FontSettingAdapter adapter;
    private OnResultListener onResultListener;
    private List<String> data = fontSize;
    private int type = 0;

    private RecyclerView fontContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.editor_font_setting_fragment, null);

        type = getArguments().getInt(TYPE);
        switch (type) {
            case TYPE_SIZE: data = fontSize; break;
            case TYPE_LINE_HGEIGHT: data = lineHeight; break;
            case TYPE_FONT_FAMILY: data = fontFamily; break;
        }
        
        initView(v);
        return v;
    }

    private void initView(View v) {

        fontContainer = (RecyclerView) v.findViewById(R.id.font_container);
        fontContainer.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new FontSettingAdapter();
        adapter.setData(data);
        adapter.setOnItemClickListener(new FontSettingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (onResultListener != null) {
                    onResultListener.onResult(data.get(position));
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction()
                            .remove(FontSettingFragment.this)
                            .show(fm.findFragmentByTag(EditorMenuFragment.class.getName()))
                            .commit();
                }
            }
        });
        fontContainer.setAdapter(adapter);
    }

    interface OnResultListener {

        void onResult(String result);
    }

    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }
}
