package com.example.administrator.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.ui.fragment.MyCardFragment;
import com.example.administrator.myapplication.ui.fragment.MyDetailFragment;
import com.example.administrator.myapplication.ui.fragment.Tab2Fragment;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-27
 */
public class MyMainActivity extends BackBaseActivity {
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.image)
    ImageView draweeView;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.main_viewpager)
    ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymain);
        setCustomTitle("");
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private String[] title = {"主页","帖子"};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new MyDetailFragment();
                default:
                    return new MyCardFragment();
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
