package com.example.administrator.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *  猪婆专用
 */
public abstract class BaseLoadFragment<T> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, Callback<T> {

    protected T mPageData;
    protected boolean isLoading;

    public
    @Nullable
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    public
    @Nullable
    @Bind(android.R.id.empty)
    View mEmptyView;
    public
    @Nullable
    @Bind(R.id.empty_text)
    TextView mEmptyText;
    public
    @Nullable
    @Bind(R.id.empty_load)
    View mEmptyLoad;
    public
    @Nullable
    @Bind(R.id.empty_button)
    TextView mEmptyButton;
    public
    @Nullable
    @Bind(R.id.empty_progressbar)
    View mEmptyProgressbar;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mPageData == null) {
            onLoadData();
        } else {
            onInitLoadData(mPageData);
        }
        if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {//刷新数据
        onLoadData();
    }

    protected abstract void onLoadData();

    //TODO 当前初始化数据 没有判断 ErrorMessage~
    protected abstract void onInitLoadData(T pageData);

    @Override
    public void success(T page, Response response) {
        if (getActivity() == null) return;
        stopRefresh();
        setPageData(page);
    }

    @Override
    public void failure(RetrofitError error) {
        if (getActivity() == null) return;
        stopRefresh();
        showConnectionRetry();
    }

    protected void stopRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);//停止刷新
        }
    }

    protected void setPageData(T page_data) {
        this.mPageData = null;
        this.mPageData = page_data;
        onInitLoadData(page_data);

    }

    protected void showLoad() {
        if (getActivity() == null) return;
        showLoad(getString(R.string.loading));
    }

    protected void showLoad(String str) {
        if (getActivity() == null) return;
        if (mPageData != null) return;
        if (mEmptyView != null) mEmptyView.setVisibility(View.VISIBLE);
        if (mEmptyLoad != null) mEmptyLoad.setVisibility(View.VISIBLE);
        if (mEmptyProgressbar != null) mEmptyProgressbar.setVisibility(View.VISIBLE);
        if (mEmptyText != null) mEmptyText.setText(R.string.loading);
        if (mEmptyButton != null) mEmptyButton.setVisibility(View.GONE);
    }


    protected void showConnectionRetry() {
        if (getActivity() == null) return;
        showConnectionRetry("点击重试");
    }

    protected void showConnectionRetry(String str) {
        if (getActivity() == null) return;
        if (mPageData != null) return;
        if (mEmptyView != null) mEmptyView.setVisibility(View.VISIBLE);
        if (mEmptyLoad != null) mEmptyLoad.setVisibility(View.GONE);
        if (mEmptyButton != null) {
            mEmptyButton.setText(str);
            mEmptyButton.setVisibility(View.VISIBLE);
            mEmptyButton.setOnClickListener(mOnClickListener);
        }
    }


    protected void hideEmptyView() {
        if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
    }

    protected View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            retryClick();
        }
    };

    protected void retryClick() {
        showLoad();
        onLoadData();
    }

}
