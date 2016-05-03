package com.example.administrator.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.CustomItemModel;
import com.example.administrator.myapplication.model.EzAction;
import com.example.administrator.myapplication.model.EzContentData;
import com.example.administrator.myapplication.model.FriendGroupListModel;
import com.example.administrator.myapplication.ui.adapter.FriendGroupAdapter;
import com.example.administrator.myapplication.ui.adapter.NewsListAdapter;
import com.example.administrator.myapplication.ui.view.DividerLine;
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
 * User: lyjq(1752095474)
 * Date: 2016-04-25
 */
public class ListViewFragment extends BaseLoadFragment<String>  implements NewsListAdapter.OnListClick, NewsListAdapter.OnLoadMoreListener {

    @Bind(R.id.list)
    ListView mListView;
    NewsListAdapter adapter;


    @Bind(R.id.pull)
    PullToRefreshView mPullRefresh;
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_listview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new NewsListAdapter(getActivity(), this,this);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onLoadData() {
        showLoad();
        initData();
    }

    @Override
    protected void onInitLoadData(String pageData) {
        hideEmptyView();
        List<CustomItemModel> list = parseJson(pageData);
        if (list != null) {
            adapter.addItems(list);
        }
        mPullRefresh.setRefreshing(false);
    }

    @Override
    public void click(CustomItemModel model) {
        EzAction ezAction = model.getEzAction();
        ActivityJumper.JumpToActivity(getActivity(), ezAction);
    }

    @Override
    public void loadMore() {
        initData();
    }

    private void initData() {
        RestAdapterUtils.getListApi().getNewsList(this);
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

}
