package com.uguke.demo.editor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.uguke.demo.editor.R;


/**
 * 功能描述：超链接
 * @author LeiJue
 * @time 2017/11/02
 */
public class EditHyperlinkFragment extends Fragment implements View.OnClickListener {

    private OnHyperlinkListener onHyperlinkListener;

    private EditText linkName;
    private EditText linkUrl;
    private Button linkCreate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_hyperlink, null);
        initView(rootView);
        return rootView;
    }



    public void setOnHyperlinkListener(OnHyperlinkListener onHyperlinkListener) {
        this.onHyperlinkListener = onHyperlinkListener;
    }

    private void initView(View rootView) {
        linkName = (EditText) rootView.findViewById(R.id.link_name);
        linkUrl = (EditText) rootView.findViewById(R.id.link_url);
        linkCreate = (Button) rootView.findViewById(R.id.link_create);

        linkCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getFragmentManager().beginTransaction().remove(this).commit();
                break;
            case R.id.link_create:
                if (onHyperlinkListener != null) {
                    onHyperlinkListener.onHyperlinkOK(
                            linkName.getText().toString(),
                            linkUrl.getText().toString());
                    getFragmentManager().beginTransaction().remove(this).commit();
                }
                break;
        }
    }


    public interface OnHyperlinkListener {
        void onHyperlinkOK(String address, String text);
    }
}
