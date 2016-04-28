package com.example.administrator.myapplication.ui.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.ui.PublishedActivity;
import com.example.administrator.myapplication.ui.TestPicActivity;

/**
 * Created by shand on 2016/4/28.
 */
public class PopupWindows extends PopupWindow {
    private final PublishedActivity mContext;
    private final OnPopupWindownButton1ClickListener mListener;


    public PopupWindows(final Context mContext, View parent, OnPopupWindownButton1ClickListener listener) {
        this.mContext = (PublishedActivity) mContext;
        this.mListener = listener;
        View view = View
                .inflate(mContext, R.layout.item_popupwindows, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view
                .findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.push_bottom_in_2));

        setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onClick();
                dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mContext,
                        TestPicActivity.class);
                mContext.startActivity(intent);
                dismiss();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    public interface OnPopupWindownButton1ClickListener{
        void onClick();
    }
}
