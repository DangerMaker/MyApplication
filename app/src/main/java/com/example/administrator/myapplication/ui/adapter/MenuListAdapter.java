package com.example.administrator.myapplication.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2016/4/12.
 */
public class MenuListAdapter extends MyBaseAdapter<String> {

    LayoutInflater layoutInflater;
    OnListClick onListClick;

    public MenuListAdapter(Context context,OnListClick onListClick) {
        super(context);
        layoutInflater = LayoutInflater.from(context);
        this.onListClick = onListClick;
    }

    public interface OnListClick{
        void click(String str);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String string = getItem(position);
        ViewHolder holder = null;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_menu,null);
            holder = new ViewHolder();
            holder.rootView = (LinearLayout)convertView.findViewById(R.id.root);
            holder.textView = (TextView)convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
            holder.textView.setText(string);
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onListClick.click(string);
                }
            });
        return convertView;
    }

    static class ViewHolder {
        TextView textView;
        LinearLayout rootView;
    }
}
