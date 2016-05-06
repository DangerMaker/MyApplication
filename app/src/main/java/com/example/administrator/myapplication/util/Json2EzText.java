package com.example.administrator.myapplication.util;

import com.example.administrator.myapplication.model.EzAction;
import com.example.administrator.myapplication.model.EzText;
import com.google.gson.Gson;

/**
 * User: lyjq(1752095474)
 * Date: 2016-05-05
 */
public class Json2EzText {

    public static EzText convert(String json){
        Gson gson = new Gson();
        String temp = json.substring(1,json.length());
        EzText ezText = gson.fromJson(temp,EzText.class);
        return ezText;
    }
}
