package com.example.administrator.myapplication.ui.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.FriendGroupItemModel;
import com.example.administrator.myapplication.ui.MyMainActivity;
import com.example.administrator.myapplication.util.DeviceUtils;
import com.example.administrator.myapplication.util.SystemUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-27
 */
public class FriendGroupItemView extends RelativeLayout implements View.OnClickListener{

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
    boolean flag = false;
    int viewWidth;
    int margin;
    Context context;

    public FriendGroupItemView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_friend_group, this);
        ButterKnife.bind(this, view);
        viewWidth = DeviceUtils.getScreenWidth(getContext()) - SystemUtils.convertDpToPixel(getContext(), 68);
        margin = SystemUtils.convertDpToPixel(getContext(), 4);
    }

    public void setData(FriendGroupItemModel data) {
        this.data = data;
        avater.setImageURI(Uri.parse(data.getEzContentData().getUserHeaderImageName()));
        name.setText(data.getEzContentData().getUserNameText());
        group.setText(data.getEzContentData().getUserMarkText());
        time.setText(data.getEzContentData().getUserTimeText());
        allcontent = data.getEzContentData().getContentText();

        if (DeviceUtils.measureLineNum(context, content, allcontent) > 6) {
            shrink.setVisibility(View.VISIBLE);
            content.setMaxLines(6);
            shrink.setText("全文");
            shrink.setOnClickListener(this);
        }else{
            shrink.setVisibility(View.GONE);
        }

        content.setText(allcontent);

        gridLayout.removeAllViews();
        for (String imageUrl : data.getEzContentData().getImageArray()) {
            View convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_drawee, gridLayout, false);
            GridLayout.LayoutParams lp = (GridLayout.LayoutParams) convertView.getLayoutParams();
            lp.width = (viewWidth - 2 * margin) / 3;
            lp.height = (viewWidth - 2 * margin) / 3;
            lp.setMargins(margin / 2, margin / 2, margin / 2, margin / 2);
            gridLayout.addView(convertView);
            ((SimpleDraweeView) convertView).setImageURI(Uri.parse(imageUrl));
//            setItemData(convertView, item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shrink:
                if(!flag){
                    content.setMaxLines(100);
                    content.setText(allcontent);
                    shrink.setText("收起");
                    flag = !flag;
                }else{
                    content.setMaxLines(6);
                    content.setText(allcontent);
                    shrink.setText("全文");
                    flag = !flag;
                }
                break;
        }
    }

    @OnClick(R.id.image)
    public void toMy(){
        context.startActivity(new Intent(context, MyMainActivity.class));
    }
}
