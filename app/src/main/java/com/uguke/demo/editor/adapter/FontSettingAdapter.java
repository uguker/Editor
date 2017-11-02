package com.uguke.demo.editor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uguke.demo.editor.R;

/**
 * 功能描述：字体属性设置适配器
 * @author LeiJue
 * @time 2017/11/02
 */
public class FontSettingAdapter extends BaseRecyclerAdapter<String, FontSettingAdapter.ViewHolder> {

    private OnItemClickListener listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.editor_font_setting_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String content = data.get(position);
        holder.content.setText(content);
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        ImageView selected;
        public ViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.item_content);
            selected = (ImageView) itemView.findViewById(R.id.item_selected);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
