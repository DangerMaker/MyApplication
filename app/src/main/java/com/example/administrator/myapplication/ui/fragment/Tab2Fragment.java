package com.example.administrator.myapplication.ui.fragment;

import android.view.View;
import android.widget.ScrollView;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.example.administrator.myapplication.R;

import butterknife.Bind;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-25
 */
public class Tab2Fragment extends BaseFragment  implements ScrollableHelper.ScrollableContainer {
    @Bind(R.id.scrollview)
    ScrollView scrollView;
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_empty;
    }

    @Override
    public View getScrollableView() {
        return scrollView;
    }
}
