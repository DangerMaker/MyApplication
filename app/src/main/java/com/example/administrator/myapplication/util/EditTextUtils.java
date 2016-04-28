package com.example.administrator.myapplication.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by shand on 2016/4/21.
 */
public class EditTextUtils {

    public static void setEditTextImage(final EditText text, final View view){
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    view.setVisibility(View.GONE);
                } else {
                    if (isHasFocus) {
                        view.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                view.setVisibility(hasFocus && (text.length() > 0) ? View.VISIBLE
                        : View.GONE);
                isHasFocus = hasFocus;
            }
        });
    }
    private static boolean isHasFocus = false;



}
