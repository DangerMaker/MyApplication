package com.example.administrator.myapplication.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.CustomItemModel;
import com.example.administrator.myapplication.model.EzAction;
import com.example.administrator.myapplication.model.EzContentData;
import com.example.administrator.myapplication.ui.adapter.MyBaseAdapter;
import com.example.administrator.myapplication.ui.adapter.NewsListAdapter;
import com.example.administrator.myapplication.ui.view.PullToRefreshView;
import com.example.administrator.myapplication.util.ActivityJumper;
import com.example.administrator.myapplication.util.Json2Map;
import com.example.administrator.myapplication.util.RestAdapterUtils;
import com.example.administrator.myapplication.util.SystemUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
public class NewsListActivity extends BackBaseActivity implements NewsListAdapter.OnListClick, NewsListAdapter.OnLoadMoreListener {
    @Bind(R.id.list)
    ListView mListView;
    NewsListAdapter adapter;

    @Bind(R.id.pull)
    PullToRefreshView mPullRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newslist);
        setCustomTitle("新闻列表");

        adapter = new NewsListAdapter(this, this,this);
        mListView.setAdapter(adapter);
        mPullRefresh.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                SystemUtils.show_msg(NewsListActivity.this,"刷新成功!");
            }
        });
        initData();
    }

//    private void initLocalData() {
//        String json = "{" + SystemUtils.getGetAssets(this, "news_json.txt");
//        Gson gson = new Gson();
//        CustemList custemList = gson.fromJson(json, CustemList.class);
//        adapter.addItems(custemList.getList());
//    }

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
                mPullRefresh.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.getMessage());
                mPullRefresh.setRefreshing(false);
            }
        });
    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }

    @Override
    public void click(CustomItemModel model) {
        EzAction ezAction = model.getEzAction();
        ActivityJumper.JumpToActivity(this, ezAction);
    }

    @Override
    public void loadMore() {
        initData();
    }
}
