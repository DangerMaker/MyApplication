package com.example.administrator.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.GridView;
import android.widget.ListView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.CustomItemModel;
import com.example.administrator.myapplication.model.EzAction;
import com.example.administrator.myapplication.model.EzContentData;
import com.example.administrator.myapplication.ui.adapter.NewsGridAdapter;
import com.example.administrator.myapplication.ui.adapter.NewsListAdapter;
import com.example.administrator.myapplication.ui.view.PullToRefreshView;
import com.example.administrator.myapplication.util.ActivityJumper;
import com.example.administrator.myapplication.util.Json2Map;
import com.example.administrator.myapplication.util.RestAdapterUtils;
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

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-25
 */
public class GridViewFragment extends BaseLoadFragment<String>  implements NewsGridAdapter.OnListClick, NewsGridAdapter.OnLoadMoreListener {

    @Bind(R.id.gridview)
    GridView mGridView;

    NewsGridAdapter adapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_gridview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new NewsGridAdapter(getActivity(), this,this);
        mGridView.setAdapter(adapter);
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
