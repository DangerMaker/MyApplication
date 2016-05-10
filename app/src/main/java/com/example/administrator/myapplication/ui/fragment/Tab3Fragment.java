package com.example.administrator.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.ui.adapter.ArticlePraiseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shand on 2016/5/4.
 */
public class Tab3Fragment extends BaseFragment implements ScrollableHelper.ScrollableContainer{
    @Bind(R.id.list_praise)
    ListView mList;
    @Bind(R.id.scrollview)
    ScrollView scrollview;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_article_praise;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArticlePraiseAdapter adapter = new ArticlePraiseAdapter(getActivity(),getActivity().getLayoutInflater());
        List<String> list = new ArrayList<>();
        for(int i = 0;i< 10;i++){
            list.add(i+"觉得很赞");
        }
        adapter.updateItems(list);
        mList.setAdapter(adapter);
    }
    @Override
    public View getScrollableView() {
        return scrollview;
    }
}
