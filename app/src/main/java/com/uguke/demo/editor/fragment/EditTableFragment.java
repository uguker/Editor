package com.uguke.demo.editor.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.uguke.demo.editor.R;


public class EditTableFragment extends Fragment implements View.OnClickListener {

    private EditText tableRows;
    private EditText tableCols;
    private Button tableCreate;
    private OnTableListener onTableListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_table, null);

        initView(rootView);
        return rootView;
    }

    public void setOnTableListener(OnTableListener onTableListener) {
        this.onTableListener = onTableListener;
    }

    private void initView(View rootView) {
        tableRows = (EditText) rootView.findViewById(R.id.table_rows);
        tableCols = (EditText) rootView.findViewById(R.id.table_cols);
        tableCreate = (Button) rootView.findViewById(R.id.table_create);

        tableCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getFragmentManager().beginTransaction().remove(this).commit();
                break;
            case R.id.table_create:
                if (onTableListener != null) {
                    onTableListener.onTableOK(Integer.valueOf(tableRows.getText().toString()),
                            Integer.valueOf(tableCols.getText().toString()));
                    getFragmentManager().beginTransaction().remove(this).commit();
                }
                break;
        }
    }


    public interface OnTableListener {
        void onTableOK(int rows, int cols);
    }
}
