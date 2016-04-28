package com.example.administrator.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.GridView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.CustomItemModel;
import com.example.administrator.myapplication.model.EzAction;
import com.example.administrator.myapplication.model.EzContentData;
import com.example.administrator.myapplication.ui.adapter.MyBaseAdapter;
import com.example.administrator.myapplication.ui.adapter.NewsGridAdapter;
import com.example.administrator.myapplication.ui.view.PullToRefreshView;
import com.example.administrator.myapplication.util.ActivityJumper;
import com.example.administrator.myapplication.util.Json2Map;
import com.example.administrator.myapplication.util.RestAdapterUtils;
import com.example.administrator.myapplication.util.SystemUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2016/4/13.
 */
public class NewsGridActivity extends BackBaseActivity implements NewsGridAdapter.OnListClick, PullToRefreshView.OnRefreshListener, NewsGridAdapter.OnLoadMoreListener {
    @Bind(R.id.gridview)
    GridView mGridView;
    @Bind(R.id.pull)
    PullToRefreshView mPull;

    NewsGridAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_gridview);
        setCustomTitle("新闻列表");

        adapter = new NewsGridAdapter(this, this,this);
        mGridView.setAdapter(adapter);
        mPull.setOnRefreshListener(this);
        initData();
    }


    private List<CustomItemModel> parseJson(String json) {
        List<CustomItemModel> modelList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = (JSONArray) object.get("list");
            for (int i = 0; i < array.length(); i++) {
                CustomItemModel itemModel = new CustomItemModel();
                JSONObject itemObject = (JSONObject) array.get(i);
                JSONObject actionObject = (JSONObject) itemObject.get("ezAction");
                //EzAction
                EzAction ezAction = gson.fromJson(actionObject.toString(), EzAction.class);
                //EzMap
                String ezMap = (String) itemObject.get("ezContentMap");
                //EzContentData
                JSONObject jsonData = (JSONObject) itemObject.get("ezContentData");
                Map<String, Object> map = new HashMap<>();
                Iterator keyIter = jsonData.keys();
                JSONObject value = null;
                while (keyIter.hasNext()) {
                    String key = (String) keyIter.next();
                    if (jsonData.get(key) instanceof String) {
                        map.put(key,jsonData.get(key));
                    } else {
                        value = (JSONObject) jsonData.get(key);
                        map.put(key, value);
                    }
                }
                EzContentData ezContentData = new EzContentData();
                ezContentData.setMap(map);
                //add views
                itemModel.setEzContentMap(Json2Map.convert(ezMap));
                itemModel.setEzAction(ezAction);
                itemModel.setEzContentData(ezContentData);
                modelList.add(itemModel);
            }
            return modelList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initData() {
        RestAdapterUtils.getListApi().getNewsList(new Callback<String>() {
            @Override
            public void success(String string, Response response) {

                List<CustomItemModel> list = parseJson(string);
                if (list != null) {
                    adapter.addItems(list);
                }
                stopRefresh();

            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.getMessage());
                stopRefresh();
            }
        });
    }

    protected void stopRefresh(){
        if (mPull != null) {
            mPull.setRefreshing(false);//停止刷新
        }
    }

    @Override
    public void click(CustomItemModel model) {
        EzAction ezAction = model.getEzAction();
        ActivityJumper.JumpToActivity(this, ezAction);
    }

    @Override
    public void onRefresh() {
        initData();
        SystemUtils.show_msg(NewsGridActivity.this,"刷新成功!");
    }

    @Override
    public void loadMore() {
        initData();
    }
}
