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
import com.example.administrator.myapplication.util.Json2EzAction;
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
public class ListViewFragment extends BaseLoadFragment<String> implements NewsListAdapter.OnListClick, NewsListAdapter.OnLoadMoreListener {

    @Bind(R.id.list)
    ListView mListView;
    NewsListAdapter adapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_listview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new NewsListAdapter(getActivity(), this, this);
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
        List<CustomItemModel> list = parseJsonNew(pageData);
        if (list != null) {
            //6条数据
            list.addAll(list);
            list.addAll(list);
            adapter.updateItems(list);
        }
    }

    @Override
    public void click(CustomItemModel model) {
        EzAction ezAction = model.getEzAction();
        ActivityJumper.JumpToActivity(getActivity(), ezAction);
    }

    @Override
    public void loadMore() {
//        initData();
    }

    private void initData() {
        RestAdapterUtils.getListApi().getNewsList(this);
    }

    private List<CustomItemModel> parseJsonNew(String json) {
        List<CustomItemModel> modelList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                CustomItemModel itemModel = new CustomItemModel();
                JSONObject object = (JSONObject)array.get(i);
                Map<String, Object> map = new HashMap<>();
                Iterator keyIter = object.keys();
                JSONObject value = null;
                while (keyIter.hasNext()) {
                    String key = (String) keyIter.next();
                    if (object.get(key) instanceof String) {
                        if(key.equals("ezMap")){
                            //set map
                            itemModel.setEzContentMap(Json2Map.convert(object.get(key).toString()));
                            continue;
                        }else if(key.equals("ezAction")){
                            // set action
                            itemModel.setEzAction(Json2EzAction.convert(object.get(key).toString()));
                        }
                        map.put(key, object.get(key));
                    } else {
                        value = (JSONObject) object.get(key);
                        map.put(key, value);
                    }
                }
                //set view
                itemModel.setMap(map);
                modelList.add(itemModel);
            }
            return modelList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
