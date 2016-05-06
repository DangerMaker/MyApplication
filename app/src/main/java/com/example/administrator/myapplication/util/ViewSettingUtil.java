package com.example.administrator.myapplication.util;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.model.EzText;

/**
 * User: lyjq(1752095474)
 * Date: 2016-05-05
 */
public class ViewSettingUtil {
    public static void setTextView(final Context context, TextView textView,final EzText ezText) {
        if (ezText.getText() != null) {
            textView.setText(ezText.getText());
        }
        if (ezText.getFontsize() != null) {
            textView.setTextSize(Float.parseFloat(ezText.getFontsize()));
        }
        if (ezText.getTextcolor() != null) {
            textView.setTextColor(Color.parseColor(ezText.getTextcolor()));
        }
        if (ezText.getBackcolor() != null) {
            textView.setBackgroundColor(Color.parseColor(ezText.getBackcolor()));
        }
        if (ezText.getAlignment() != null) {
            if (ezText.getAlignment().equals("0")) {
                textView.setGravity(Gravity.LEFT);
            } else if (ezText.getAlignment().equals("1")) {
                textView.setGravity(Gravity.CENTER);
            } else if (ezText.getAlignment().equals("2")) {
                textView.setGravity(Gravity.RIGHT);
            }
        }
        if (ezText.getEzAction() != null) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityJumper.JumpToActivity(context, ezText.getEzAction());
                }
            });
        }
    }
}
