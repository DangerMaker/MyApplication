package com.example.administrator.myapplication.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.Bimp;
import com.example.administrator.myapplication.ui.adapter.PhotoPageAdapter;
import com.example.administrator.myapplication.util.FileUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoActivity extends BackBaseActivity {

    @Bind(R.id.viewpager)
    ViewPager mPager;
    @Bind(R.id.photo_relativeLayout)
    RelativeLayout photo_relativeLayout;
    private ArrayList<View> listViews = null;
    private PhotoPageAdapter adapter;
    private int count;

    public List<Bitmap> bmp = new ArrayList<Bitmap>();
    public List<String> drr = new ArrayList<String>();
    public List<String> del = new ArrayList<String>();
    public int max;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

       initView();
    }

    private void initView() {
        photo_relativeLayout.setBackgroundColor(0x70000000);
        for (int i = 0; i < Bimp.bmp.size(); i++) {
            bmp.add(Bimp.bmp.get(i));
        }
        for (int i = 0; i < Bimp.drr.size(); i++) {
            drr.add(Bimp.drr.get(i));
        }
        max = Bimp.max;

        mPager.setOnPageChangeListener(pageChangeListener);
        for (int i = 0; i < bmp.size(); i++) {
            initListViews(bmp.get(i));//
        }

        adapter = new PhotoPageAdapter(listViews);// 构造adapter
        mPager.setAdapter(adapter);// 设置适配器
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        mPager.setCurrentItem(id);
    }

    private void initListViews(Bitmap bm) {
        if (listViews == null)
            listViews = new ArrayList<View>();
        ImageView img = new ImageView(this);// 构造textView对象
        img.setBackgroundColor(0xff000000);
        img.setImageBitmap(bm);
        img.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        listViews.add(img);// 添加view
    }

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

        public void onPageSelected(int arg0) {// 页面选择响应函数
            count = arg0;
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };



    @OnClick(R.id.photo_bt_exit)
    public void photoExit() {
        finish();
    }

    @OnClick(R.id.photo_bt_del)
    public void photoDel() {
        if (listViews.size() == 1) {
            Bimp.bmp.clear();
            Bimp.drr.clear();
            Bimp.max = 0;
            FileUtils.deleteDir();
            finish();
        } else {
            String newStr = drr.get(count).substring(
                    drr.get(count).lastIndexOf("/") + 1,
                    drr.get(count).lastIndexOf("."));
            bmp.remove(count);
            drr.remove(count);
            del.add(newStr);
            max--;
            mPager.removeAllViews();
            listViews.remove(count);
            adapter.setListViews(listViews);
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.photo_bt_enter)
    public void photoEnter() {
        Bimp.bmp = bmp;
        Bimp.drr = drr;
        Bimp.max = max;
        for (int i = 0; i < del.size(); i++) {
            FileUtils.delFile(del.get(i) + ".JPEG");
        }
        finish();
    }
}
