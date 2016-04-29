package com.example.administrator.myapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.ImageBucket;
import com.example.administrator.myapplication.ui.adapter.ImageBucketAdapter;
import com.example.administrator.myapplication.util.AlbumHelper;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TestPicActivity extends BackBaseActivity {
    List<ImageBucket> dataList;
    ImageBucketAdapter adapter;// 自定义的适配器
    AlbumHelper helper;
    public static final String EXTRA_IMAGE_LIST = "imagelist";
    public static Bitmap bimap;
    @Bind(R.id.gridview)
    GridView mGridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_bucket);
        setCustomTitle("相机胶卷");
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        dataList = helper.getImagesBucketList(false);
        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.icon_addpic_unfocused);
    }

    /**
     * 初始化view视图
     */
    private void initView() {
        adapter = new ImageBucketAdapter(TestPicActivity.this, dataList);
        mGridview.setAdapter(adapter);

        mGridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /**
                 * 根据position参数，可以获得跟GridView的子View相绑定的实体类，然后根据它的isSelected状态，
                 * 来判断是否显示选中效果。 至于选中效果的规则，下面适配器的代码中会有说明
                 */
                /**
                 * 通知适配器，绑定的数据发生了改变，应当刷新视图
                 */
                Intent intent = new Intent(TestPicActivity.this,
                        ImageGridActivity.class);
                intent.putExtra(TestPicActivity.EXTRA_IMAGE_LIST,
                        (Serializable) dataList.get(position).imageList);
                startActivity(intent);
                finish();
            }

        });
    }
}
