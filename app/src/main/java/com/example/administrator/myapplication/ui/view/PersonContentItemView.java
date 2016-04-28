package com.example.administrator.myapplication.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shand on 2016/4/18.
 */
public class PersonContentItemView extends FrameLayout {
    private final static String NAME_SPACE = "http://schemas.android.com/apk/com.example.administrator.myapplication";
    @Bind(R.id.tv_text)
    TextView mTitle;
    @Bind(R.id.iv_image)
    ImageView mImage;

    public PersonContentItemView(Context context) {
        this(context, null);
    }

    public PersonContentItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PersonContentItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        String title = attrs.getAttributeValue(NAME_SPACE, "userText");
        boolean isShow = attrs.getAttributeBooleanValue(NAME_SPACE, "isShowImage", false);
        View view = inflate(getContext(), R.layout.item_personcontent, this);
        ButterKnife.bind(view);
        initData(title,isShow);
    }

    private void initData(String title, boolean isShow) {
        mTitle.setText(title);
        if(isShow){
            mImage.setVisibility(View.VISIBLE);
        }else{
            mImage.setVisibility(View.GONE);
        }
    }
}
