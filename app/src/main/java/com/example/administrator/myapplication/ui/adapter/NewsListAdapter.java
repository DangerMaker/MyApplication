package com.example.administrator.myapplication.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.Config;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.CustomItemModel;
import com.example.administrator.myapplication.model.EzImage;
import com.example.administrator.myapplication.model.EzText;
import com.example.administrator.myapplication.model.ViewModel;
import com.example.administrator.myapplication.util.Json2EzText;
import com.example.administrator.myapplication.util.ViewSettingUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/12.
 */
public class NewsListAdapter extends MyBaseAdapter<CustomItemModel> {

    private final OnLoadMoreListener mListener;
    LayoutInflater layoutInflater;
    OnListClick onListClick;
    Gson gson;

    public NewsListAdapter(Context context, OnListClick onListClick, OnLoadMoreListener listener) {
        super(context);
        gson = new Gson();
        layoutInflater = LayoutInflater.from(context);
        this.onListClick = onListClick;
        this.mListener = listener;
    }

    public interface OnListClick {
        void click(CustomItemModel customItemModel);
    }

    public interface OnLoadMoreListener {
        void loadMore();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CustomItemModel model = getItem(position);
        convertView = layoutInflater.inflate(R.layout.item_news, null);
        RelativeLayout rootView = (RelativeLayout) convertView.findViewById(R.id.root);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListClick.click(model);
            }
        });

        Iterator<Map.Entry<String, Object>> iterator = model.getMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            View view = convertView.findViewWithTag(model.getEzContentMap().get(key));

            if (view instanceof TextView) {
                if (value.toString().startsWith("@")) {
                    EzText ezText = Json2EzText.convert(value.toString());
                    ViewSettingUtil.setTextView(mContext,(TextView)view,ezText);
                } else {
                    ((TextView) view).setText((String) value);
                }
            } else if (view instanceof SimpleDraweeView) {
                if (value instanceof String) {
                    ((SimpleDraweeView) view).setImageURI(Uri.parse(value.toString()));
                } else {
                    EzImage ezImage = gson.fromJson(value.toString(), EzImage.class);
                    ((SimpleDraweeView) view).setImageURI(Uri.parse(Config.IMAGE_URL + File.separator + ezImage.getFilename()));
                }
            }
        }

        if (position == getCount() - 1) {
            mListener.loadMore();
        }
        return convertView;
    }

}
