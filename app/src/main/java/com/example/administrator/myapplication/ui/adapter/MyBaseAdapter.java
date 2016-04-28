package com.example.administrator.myapplication.ui.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.example.administrator.myapplication.model.CustomItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected List<T> mItems ;
    protected Context mContext;
    public MyBaseAdapter(Context context){
        this.mContext = context;
        mItems = new ArrayList<>();
    }

    public List<T> getList() {
        return mItems;
    }

    public void addItem(T item) {
        if(item == null) return ;
        mItems.add(mItems.size(), item);
        notifyDataSetChanged();
    }

    public void addItems(List<T> items){
        if(items == null) return ;
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    public boolean containsAll(List<T> items){
        return mItems.containsAll(items);
    }

    public void updateItem(T tasks, int position) {
        if(tasks == null) return ;
        mItems.set(position, tasks);
        notifyDataSetChanged();
    }

    public void updateItems(List<T> items){
        if(items == null) return;
        this.mItems.clear();
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void removeItem(int index) {
        mItems.remove(index);
        notifyDataSetChanged();
    }


    public T getItem(int location) {
        return mItems.get(location);
    }

    @Override
    public int getCount() {
        return  mItems == null ? 0 : mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear(){
        mItems.clear();
        notifyDataSetChanged();
    }
}
