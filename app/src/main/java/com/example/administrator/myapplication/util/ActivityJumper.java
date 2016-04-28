package com.example.administrator.myapplication.util;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.myapplication.model.EzAction;
import com.example.administrator.myapplication.ui.NewsDetailActivity;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ActivityJumper {

    public static void JumpToActivity(Context ctx, EzAction action) {
        Intent intent = new Intent();
        if(action.getEzActionType().equals("showPage")){
            intent.putExtra("url",action.getEzTargetData().getEzUri());
            intent.setClass(ctx, NewsDetailActivity.class);
        }
        ctx.startActivity(intent); 
    }
}
