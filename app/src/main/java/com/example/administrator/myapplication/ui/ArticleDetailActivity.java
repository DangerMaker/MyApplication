package com.example.administrator.myapplication.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.FriendGroupItemModel;
import com.example.administrator.myapplication.ui.fragment.BaseFragment;
import com.example.administrator.myapplication.ui.fragment.Tab2Fragment;
import com.example.administrator.myapplication.ui.fragment.Tab3Fragment;
import com.example.administrator.myapplication.util.DeviceUtils;
import com.example.administrator.myapplication.util.SystemUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

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
    ArrayList<BaseFragment> fragmentList = new ArrayList<>();
    private String[] dialogString = {"举报","帮上头条","收藏","分享"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail1);
        setCustomTitle("微博正文");
        data = (FriendGroupItemModel)getIntent().getParcelableExtra("data");
        List<FriendGroupItemModel.EzContentDataBean.CommentArrayBean> list = data.getEzContentData().getCommentArray();
        Tab2Fragment fragment = new Tab2Fragment();
        fragment.initDatas(list);
        fragmentList.add(fragment);
        Tab3Fragment fragment1 = new Tab3Fragment();
        fragmentList.add(fragment1);

        mScrollLayout = (ScrollableLayout) findViewById(R.id.scrollableLayout);
        mScrollLayout.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
//                ViewHelper.setTranslationY(imageHeader, (float) (currentY * 0.5));
            }
        });
        mScrollLayout.getHelper().setCurrentScrollableContainer((ScrollableHelper.ScrollableContainer) fragmentList.get(0));

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
                mScrollLayout.getHelper().setCurrentScrollableContainer((ScrollableHelper.ScrollableContainer) fragmentList.get(position));
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
        for(int i = 0 ; i < data.getEzContentData().getImageArray().size() ; i ++){
            String imageUrl = data.getEzContentData().getImageArray().get(i);
            final View convertView = LayoutInflater.from(this).inflate(R.layout.item_drawee, gridLayout, false);
            GridLayout.LayoutParams lp = (GridLayout.LayoutParams) convertView.getLayoutParams();
            convertView.setTag(i);
            lp.width = (viewWidth - 2 * margin) / 3;
            lp.height = (viewWidth - 2 * margin) / 3;
            lp.setMargins(margin / 2, margin / 2, margin / 2, margin / 2);
            gridLayout.addView(convertView);
            ((SimpleDraweeView) convertView).setImageURI(Uri.parse(imageUrl));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ArticleDetailActivity.this, PhotoActivity1.class);
                    intent.putExtra("images",(Serializable)data.getEzContentData().getImageArray());
                    intent.putExtra("ID",(int)convertView.getTag());
                    intent.putExtra("type",1);
                    startActivity(intent);
                }
            });
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


    @OnClick(R.id.more)
    public void more(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ListView listView = new ListView(ArticleDetailActivity.this);
        listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,dialogString));
       final AlertDialog dialog = builder.setView(listView).show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                SystemUtils.show_msg(ArticleDetailActivity.this,dialogString[position]);
            }
        });
    }
}
