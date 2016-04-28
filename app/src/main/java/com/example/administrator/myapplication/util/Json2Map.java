package com.example.administrator.myapplication.util;

import java.util.HashMap;
import java.util.Map;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-19
 */
public class Json2Map {

    public static Map<String,String> convert(String json){
        Map<String,String> map = new HashMap<String,String>();
        String item[] = json.split(",");
        if(item !=null){
            for (int i = 0; i < item.length; i++) {
                String[] strs = item[i].split(":");
                map.put(strs[0],strs[1]);
            }
            return map;
        }
        return null;
    }
}
