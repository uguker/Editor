package com.uguke.demo.editor.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerView基础Adapter
 */
public abstract class BaseRecyclerAdapter<T, V extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<V> {

    protected List<T> data;

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * 设置数据
     * @param data
     */
    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void insertItem(int position, T item) {
        if (position > -1 && position < getItemCount()) {
            data.add(item);
            notifyItemInserted(position);
        }
    }

    public void removeItem(int position, T item) {
        if (position > -1 && position < getItemCount()) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }
}
