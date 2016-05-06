package com.example.administrator.myapplication.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.FriendGroupItemModel;
import com.facebook.drawee.view.SimpleDraweeView;


/**
 * Created by shand on 2016/5/4.
 */
public class ArticleCommentAdapter extends MyBaseAdapter<FriendGroupItemModel.EzContentDataBean.CommentArrayBean> {

    private final LayoutInflater layoutInflater;

    public ArticleCommentAdapter(Context context, LayoutInflater layoutInflater) {
        super(context);
        this.layoutInflater = layoutInflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_comment,null);
            holder.image = (SimpleDraweeView) convertView.findViewById(R.id.image);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.comment = (TextView) convertView.findViewById(R.id.comment);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        FriendGroupItemModel.EzContentDataBean.CommentArrayBean data = getItem(position);
        holder.username.setText(data.getCriticidname());
        holder.comment.setText(data.getContent());
        if(data.getTargetidname() != null)
        holder.time.setText(data.getTargetidname());
        return convertView;
    }

    class ViewHolder{
        public SimpleDraweeView image;
        public TextView username;
        public TextView time;
        public TextView comment;
    }
}
