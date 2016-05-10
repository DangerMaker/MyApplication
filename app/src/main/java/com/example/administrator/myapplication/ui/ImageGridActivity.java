package com.example.administrator.myapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.Bimp;
import com.example.administrator.myapplication.model.ImageBucket;
import com.example.administrator.myapplication.model.ImageItem;
import com.example.administrator.myapplication.ui.adapter.ImageGridAdapter;
import com.example.administrator.myapplication.util.AlbumHelper;
import com.example.administrator.myapplication.util.SystemUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageGridActivity extends BackBaseActivity implements OnClickListener {
    List<ImageBucket> imageBuckets;
	List<ImageItem> dataList;
    ImageGridAdapter adapter;
    AlbumHelper helper;
    public static Bitmap bimap;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    SystemUtils.show_msg(ImageGridActivity.this,"最多选择9张图片");
                    break;
            }
        }
    };
    @Bind(R.id.gridview)
    GridView mGridview;
    @Bind(R.id.btn_finish)
    Button mFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid);
        setCustomTitle("相册胶卷");
        helper = AlbumHelper.getHelper();
        //初始化帮助类
        helper.init(getApplicationContext());
        initData();
        initView();
    }

    private void initData() {
        //获取相册的所有图片
        imageBuckets = helper.getImagesBucketList(false);
        dataList = new ArrayList<>();
        for(ImageBucket bucket : imageBuckets){
            for(ImageItem item : bucket.imageList){
                dataList.add(item);
            }
        }
        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.icon_addpic_unfocused);
    }

    private void initView() {
        mGridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new ImageGridAdapter(this,dataList,mHandler);
        mGridview.setAdapter(adapter);
        adapter.setTextCallback(new ImageGridAdapter.TextCallback() {
            public void onListen(int count) {
                mFinish.setText("已选择" + "(" + count + ")");
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.btn_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_finish:
                ArrayList<String> list = new ArrayList<>();
                Collection<String> c = adapter.map.values();
                Iterator<String> it = c.iterator();
                for (; it.hasNext(); ) {
                    list.add(it.next());
                }

                if (Bimp.act_bool) {
                    Intent intent = new Intent(ImageGridActivity.this,
                            PublishedActivity.class);
                    startActivity(intent);
                    Bimp.act_bool = false;
                }
                for (int i = 0; i < list.size(); i++) {
                    if (Bimp.drr.size() < 9) {
                        Bimp.drr.add(list.get(i));
                    }
                }
                finish();
                break;
        }
    }
}
