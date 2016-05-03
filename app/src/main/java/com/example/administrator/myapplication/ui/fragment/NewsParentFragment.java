package com.example.administrator.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.myapplication.R;
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
public class NewsParentFragment extends BaseFragment{
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.main_viewpager)
    ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tab3;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private String[] title = {"ListView","GridView"};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ListViewFragment();
                default:
                    return new GridViewFragment();
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
