package com.example.administrator.myapplication.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.CustomItemModel;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/12.
 */
public class NewsGridAdapter extends MyBaseAdapter<CustomItemModel> {

    private final OnLoadMoreListener mListener;
    LayoutInflater layoutInflater;
    OnListClick onListClick;

    public NewsGridAdapter(Context context, OnListClick onListClick,OnLoadMoreListener listener) {
        super(context);
        layoutInflater = LayoutInflater.from(context);
        this.onListClick = onListClick;
        this.mListener = listener;
    }

    public interface OnListClick {
        void click(CustomItemModel str);
    }
    public interface OnLoadMoreListener{
        void loadMore();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CustomItemModel model = getItem(position);
        convertView = layoutInflater.inflate(R.layout.item_news_grid, null);
        RelativeLayout rootView = (RelativeLayout) convertView.findViewById(R.id.root);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListClick.click(model);
            }
        });
        Iterator<Map.Entry<String, Object>> iterator = model.getEzContentData().getMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            View view = convertView.findViewWithTag(model.getEzContentMap().get(key));

            if(view == null)
                continue;

            try {
                if (view instanceof TextView) {
                    if (value instanceof String) {
                        ((TextView) view).setText((String) value);
                    } else {
                        ((TextView) view).setText((String) ((JSONObject) value).get("ezTitle"));
                    }
                } else if (view instanceof SimpleDraweeView) {
                    if (value instanceof String) {
                        ((SimpleDraweeView) view).setImageURI(Uri.parse((String) value));
                    } else {
                        ((SimpleDraweeView) view).setImageURI(Uri.parse((String) ((JSONObject) value).get("ezUri")));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(position == getCount() -1){
            mListener.loadMore();
        }
        return convertView;
    }
}
