package com.example.administrator.myapplication.util;

import com.example.administrator.myapplication.model.EzAction;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * User: lyjq(1752095474)
 * Date: 2016-05-05
 */
public class Json2EzAction {

    public static EzAction convert(String json){
        Gson gson = new Gson();
        String temp = json.substring(1,json.length());
        EzAction ezAction = gson.fromJson(temp,EzAction.class);
        return ezAction;
    }
}
