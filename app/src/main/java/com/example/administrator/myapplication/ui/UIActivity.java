package com.example.administrator.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.ui.fragment.PersonCenterFragment;
import com.example.administrator.myapplication.ui.fragment.Tab1Fragment;
import com.example.administrator.myapplication.ui.fragment.Tab2Fragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-25
 */
public class UIActivity extends BaseActivity {
    @Bind(android.R.id.tabhost)
    FragmentTabHost mTabHost;
    @Bind(R.id.btn_go_back)
    RelativeLayout goBack;
    @Bind(R.id.toolbar_title)
    TextView title;
    @Bind(R.id.btn_go_next)
    RelativeLayout goNext;

    public static final String tab1 = "首页";
    public static final String tab2 = "课堂";
    public static final String tab3 = "我的";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        setCustomTitle(tab1);
        title.setText(tab1);
        goBack.setVisibility(View.INVISIBLE);
        goNext.setVisibility(View.VISIBLE);
        initView();
    }

    private void initView() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        View indicator = getIndicatoreView(tab1, R.layout.home_indicator);
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator(indicator), Tab1Fragment.class, null);

        indicator = getIndicatoreView(tab2, R.layout.home_indicator);
        mTabHost.addTab(mTabHost.newTabSpec("live").setIndicator(indicator), Tab2Fragment.class, null);

        indicator = getIndicatoreView(tab3, R.layout.home_indicator);
        mTabHost.addTab(mTabHost.newTabSpec("my").setIndicator(indicator), PersonCenterFragment.class, null);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("home")) {
                    goNext.setVisibility(View.VISIBLE);
                    title.setText(tab1);
                }else if(tabId.equals("live")){
                    goNext.setVisibility(View.GONE);
                    title.setText(tab2);
                }else if(tabId.equals("my")){
                    goNext.setVisibility(View.GONE);
                    title.setText(tab3);
                }
            }
        });
    }

    private View getIndicatoreView(String name, int layoutId) {

        View v = getLayoutInflater().inflate(layoutId, null);
        TextView tv = (TextView) v.findViewById(R.id.tabText);
        tv.setText(name);
        ImageView iv = (ImageView) v.findViewById(R.id.tabImg);

        if (name.equals(tab1)) {
            iv.setImageResource(R.drawable.shouye2);
        } else if (name.equals(tab2)) {
            iv.setImageResource(R.drawable.ketang2);
        } else if (name.equals(tab3)) {
            iv.setImageResource(R.drawable.geren2);
        }
        return v;
    }

    @OnClick(R.id.btn_go_next)
    public void goSendCircle(){
        startActivity(new Intent(this,PublishedActivity.class));
    }

}
