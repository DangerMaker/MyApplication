package com.example.administrator.myapplication.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by shand on 2016/5/4.
 */
public class ArticlePraiseAdapter extends MyBaseAdapter<String> {
    private final LayoutInflater layoutInflater;

    public ArticlePraiseAdapter(Context context, LayoutInflater layoutInflater) {
        super(context);
        this.layoutInflater = layoutInflater;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_praise,null);
            holder.image = (SimpleDraweeView) convertView.findViewById(R.id.image);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        String praise = getItem(position);
        //TODO praise
        holder.username.setText(praise);
        return convertView;
    }

    class ViewHolder{
        public SimpleDraweeView image;
        public TextView username;
    }
}
