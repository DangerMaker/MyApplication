package com.example.administrator.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.Bimp;
import com.example.administrator.myapplication.model.ImageItem;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by shand on 2016/5/5.
 */
public class PhotoActivity1 extends BackBaseActivity {

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.count)
    TextView mCount;
    private ArrayList<ImageItem> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo1);
        Intent intent = getIntent();
        list = (ArrayList<ImageItem>)intent.getSerializableExtra("images");
        int count = intent.getIntExtra("ID", 0);
        initView(count);
    }

    private void initView(int location) {
        viewpager.setAdapter(new PhotoAdapter(this));
        viewpager.setCurrentItem(location);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mCount.setText(position+1+" / "+list.size());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class PhotoAdapter extends PagerAdapter {

        private final PhotoActivity1 act;

        public PhotoAdapter(PhotoActivity1 act){
            this.act = act;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = View.inflate(PhotoActivity1.this, R.layout.item_photo1, null);
            SimpleDraweeView simpleView = (SimpleDraweeView) view.findViewById(R.id.image);
            final ImageItem item = list.get(position);
            final String path = item.imagePath;
            simpleView.setImageBitmap(Bimp.revitionImageSize(path));
            simpleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    act.finish();
                }
            });
            container.addView(view);
            return view;
        }
    }
}
