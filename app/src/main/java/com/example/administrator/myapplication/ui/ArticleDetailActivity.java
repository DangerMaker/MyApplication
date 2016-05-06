package com.example.administrator.myapplication.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.FriendGroupItemModel;
import com.example.administrator.myapplication.ui.fragment.Tab2Fragment;
import com.example.administrator.myapplication.util.DeviceUtils;
import com.example.administrator.myapplication.util.SystemUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-27
 */
public class ArticleDetailActivity extends BackBaseActivity {
    @Bind(R.id.scrollableLayout)
    ScrollableLayout mScrollLayout;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.main_viewpager)
    ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Bind(R.id.image)
    SimpleDraweeView avater;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.group)
    TextView group;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.gridlayout)
    GridLayout gridLayout;
    @Bind(R.id.shrink)
    TextView shrink;

    FriendGroupItemModel data;
    String allcontent;
    int viewWidth;
    int margin;
    ArrayList<Tab2Fragment> fragmentList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail1);
        setCustomTitle("微博正文");
        data = (FriendGroupItemModel)getIntent().getParcelableExtra("data");

        fragmentList.add(new Tab2Fragment());
        fragmentList.add(new Tab2Fragment());

        mScrollLayout = (ScrollableLayout) findViewById(R.id.scrollableLayout);
        mScrollLayout.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
//                ViewHelper.setTranslationY(imageHeader, (float) (currentY * 0.5));
            }
        });
        mScrollLayout.getHelper().setCurrentScrollableContainer(fragmentList.get(0));

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mScrollLayout.getHelper().setCurrentScrollableContainer(fragmentList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewWidth = DeviceUtils.getScreenWidth(this) - SystemUtils.convertDpToPixel(this, 68);
        margin = SystemUtils.convertDpToPixel(this, 4);

        setData();
    }

    public void setData() {
        avater.setImageURI(Uri.parse(data.getEzContentData().getUserHeaderImageName()));
        name.setText(data.getEzContentData().getUserNameText());
        group.setText(data.getEzContentData().getUserMarkText());
        time.setText(data.getEzContentData().getUserTimeText());
        allcontent = data.getEzContentData().getContentText();

        content.setText(allcontent);

        gridLayout.removeAllViews();
        for (String imageUrl : data.getEzContentData().getImageArray()) {
            View convertView = LayoutInflater.from(this).inflate(R.layout.item_drawee, gridLayout, false);
            GridLayout.LayoutParams lp = (GridLayout.LayoutParams) convertView.getLayoutParams();
            lp.width = (viewWidth - 2 * margin) / 3;
            lp.height = (viewWidth - 2 * margin) / 3;
            lp.setMargins(margin / 2, margin / 2, margin / 2, margin / 2);
            gridLayout.addView(convertView);
            ((SimpleDraweeView) convertView).setImageURI(Uri.parse(imageUrl));
        }
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private String[] title = {"评论","点赞"};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
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
