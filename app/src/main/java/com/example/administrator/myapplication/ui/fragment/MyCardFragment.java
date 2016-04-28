package com.example.administrator.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.myapplication.Config;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.FriendGroupItemModel;
import com.example.administrator.myapplication.model.FriendGroupListModel;
import com.example.administrator.myapplication.ui.adapter.FriendGroupAdapter;
import com.example.administrator.myapplication.ui.view.DividerLine;
import com.example.administrator.myapplication.util.RestAdapterUtils;
import com.example.administrator.myapplication.util.SystemUtils;

import butterknife.Bind;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-25
 */
public class MyCardFragment extends BaseLoadFragment<FriendGroupListModel> {

    @Bind(R.id.list)
    RecyclerView mRecyclerView;

    FriendGroupAdapter adapter;
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_friend_baseinfo;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(adapter == null ? adapter = new FriendGroupAdapter(getActivity()) : adapter);
            mRecyclerView.setItemViewCacheSize(5);
            DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
            dividerLine.setSize(SystemUtils.convertDpToPixel(getActivity(),1));
            dividerLine.setColor(getResources().getColor(R.color.home_backgroundcolor));
            mRecyclerView.addItemDecoration(dividerLine);
        }
    }

    @Override
    protected void onLoadData() {
        showLoad();
        RestAdapterUtils.getFriendApi().getFriendGroupList(this);
    }

    @Override
    protected void onInitLoadData(FriendGroupListModel pageData) {
        hideEmptyView();
        if(pageData != null){
            adapter.updateItems(pageData.getList());
        }
    }
}
