package com.example.administrator.myapplication.ui.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.FriendGroupItemModel;
import com.example.administrator.myapplication.ui.ArticleDetailActivity;
import com.example.administrator.myapplication.ui.MyMainActivity;
import com.example.administrator.myapplication.ui.PhotoActivity1;
import com.example.administrator.myapplication.util.DeviceUtils;
import com.example.administrator.myapplication.util.SystemUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-27
 */
public class FriendGroupItemView extends RelativeLayout implements View.OnClickListener {

    private static final int FRIEND_PIC = 1;
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
    @Bind(R.id.zan)
    RadioButton zan;
    @Bind(R.id.username_1)
    TextView username1;
    @Bind(R.id.comment_1)
    TextView comment1;
    @Bind(R.id.comment_group_1)
    LinearLayout commentGroup1;
    @Bind(R.id.username_2)
    TextView username2;
    @Bind(R.id.comment_2)
    TextView comment2;
    @Bind(R.id.comment_group_2)
    LinearLayout commentGroup2;
    @Bind(R.id.username_3)
    TextView username3;
    @Bind(R.id.comment_3)
    TextView comment3;
    @Bind(R.id.comment_group_3)
    LinearLayout commentGroup3;
    @Bind(R.id.all_comment)
    TextView allComment;
    @Bind(R.id.comment)
    TextView comment;
    @Bind(R.id.linearComment)
    LinearLayout linearComment;


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

    public void setData(final FriendGroupItemModel data) {
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
        } else {
            shrink.setVisibility(View.GONE);
        }

        content.setText(allcontent);

        gridLayout.removeAllViews();
        for(int i = 0 ; i < data.getEzContentData().getImageArray().size() ; i ++){
            String imageUrl = data.getEzContentData().getImageArray().get(i);
            final View convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_drawee, gridLayout, false);
            convertView.setTag(i);
            GridLayout.LayoutParams lp = (GridLayout.LayoutParams) convertView.getLayoutParams();
            lp.width = (viewWidth - 2 * margin) / 3;
            lp.height = (viewWidth - 2 * margin) / 3;
            lp.setMargins(margin / 2, margin / 2, margin / 2, margin / 2);
            gridLayout.addView(convertView);
            ((SimpleDraweeView) convertView).setImageURI(Uri.parse(imageUrl));
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), PhotoActivity1.class);
                    intent.putExtra("images",(Serializable)data.getEzContentData().getImageArray());
                    intent.putExtra("ID",(int)convertView.getTag());
                    intent.putExtra("type",FRIEND_PIC);
                    getContext().startActivity(intent);
                }
            });
        }




        comment.setText(data.getEzContentData().getComment().substring(2));
        zan.setText(data.getEzContentData().getPraise().substring(2));

        allComment.setOnClickListener(this);

        //评论
        List<FriendGroupItemModel.EzContentDataBean.CommentArrayBean> commentList = data.getEzContentData().getCommentArray();
        if(commentList.size() != 0){
            linearComment.setVisibility(VISIBLE);
            if (commentList.size() == 1) {
                commentGroup1.setVisibility(VISIBLE);
                comment1.setText(commentList.get(0).getContent());
                username1.setText(commentList.get(0).getCriticidname() + " :");
                commentGroup2.setVisibility(GONE);
                commentGroup3.setVisibility(GONE);
                allComment.setVisibility(GONE);
            } else if (commentList.size() == 2) {
                commentGroup1.setVisibility(VISIBLE);
                comment1.setText(commentList.get(0).getContent());
                username1.setText(commentList.get(0).getCriticidname() + " :");
                commentGroup2.setVisibility(VISIBLE);
                comment2.setText(commentList.get(1).getContent());
                username2.setText(commentList.get(1).getCriticidname() + " :");
                commentGroup3.setVisibility(GONE);
                allComment.setVisibility(GONE);
            } else if (commentList.size() >= 3) {
                commentGroup1.setVisibility(VISIBLE);
                comment1.setText(commentList.get(0).getContent());
                username1.setText(commentList.get(0).getCriticidname() + " :");
                commentGroup2.setVisibility(VISIBLE);
                comment2.setText(commentList.get(1).getContent());
                username2.setText(commentList.get(1).getCriticidname() + " :");
                commentGroup3.setVisibility(VISIBLE);
                comment3.setText(commentList.get(2).getContent());
                username3.setText(commentList.get(2).getCriticidname() + " :");
                allComment.setVisibility(VISIBLE);
            }
        }else {
            linearComment.setVisibility(GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shrink:
                if (!flag) {
                    content.setMaxLines(100);
                    content.setText(allcontent);
                    shrink.setText("收起");
                    flag = !flag;
                } else {
                    content.setMaxLines(6);
                    content.setText(allcontent);
                    shrink.setText("全文");
                    flag = !flag;
                }
                break;

            case R.id.all_comment:
                toDetai();
                break;
        }
    }

    private boolean isPraise = false;

    @OnClick(R.id.zan)
    public void toPraise(){
        int zanNumber = Integer.parseInt(zan.getText().toString());
        if(isPraise){
            zanNumber -= 1;
            SystemUtils.show_msg(getContext(),"已取消赞");
            zan.setChecked(false);
            isPraise = false;
        }else{
            SystemUtils.show_msg(getContext(),"已成功赞");
            zanNumber += 1;
            zan.setChecked(true);
            isPraise = true;
        }
        zan.setText(zanNumber+"");
    }

    @OnClick(R.id.image)
    public void toMy() {
        context.startActivity(new Intent(context, MyMainActivity.class));
    }

    @OnClick(R.id.content)
    public void toDetail() {
        toDetai();
    }

    private void toDetai() {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }
}
