package com.example.administrator.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *  为drupal7服务器专门设计的加载其网络请求类，所有有网络请求的fragment类继承它
 *  并实现它的抽象方法
 *
 */
public abstract class BaseLoadFragment<T> extends BaseFragment implements PtrHandler, Callback<T> {

    protected T mPageData;
    protected boolean isLoading;

    public
    @Nullable
    @Bind(R.id.swipe_refresh)
    PtrClassicFrameLayout mSwipeRefreshLayout;
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
        //如果mPageData有缓存直接显示，否则去网络获取
        if (mPageData == null) {
            onLoadData();
        } else {
            onInitLoadData(mPageData);
        }
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setLastUpdateTimeRelateObject(this);
            mSwipeRefreshLayout.setPtrHandler(this);
        }
    }

    //下拉刷新检测方法
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    //下拉刷新执行动作
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        onLoadData();
    }

    //网络请求的发起所需的参数会涉及到用户界面的操作，所以交给子类去发起请求
    protected abstract void onLoadData();

    //成功获得module,返回给子类去进行界面展示
    protected abstract void onInitLoadData(T pageData);

    //网络请求成功回调接口，停止下拉，数据返回给子类
    @Override
    public void success(T page, Response response) {
        if (getActivity() == null) return;
        stopRefresh();
        setPageData(page);
    }

    //网络请求失败回调接口，停止下拉，显示retry
    @Override
    public void failure(RetrofitError error) {
        if (getActivity() == null) return;
        stopRefresh();
        showConnectionRetry();
    }

    protected void stopRefresh() {
        if (mSwipeRefreshLayout != null) {
//            mSwipeRefreshLayout.setRefreshing(false);//停止刷新
            mSwipeRefreshLayout.refreshComplete();
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
