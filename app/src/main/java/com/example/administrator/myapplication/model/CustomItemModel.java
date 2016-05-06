package com.example.administrator.myapplication.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/13.
 */
public class CustomItemModel {
    Map<String,String> ezContentMap;
    EzAction ezAction;

    public Map<String, String> getEzContentMap() {
        return ezContentMap;
    }

    public void setEzContentMap(Map<String, String> ezContentMap) {
        this.ezContentMap = ezContentMap;
    }

    public EzAction getEzAction() {
        return ezAction;
    }

    public void setEzAction(EzAction ezAction) {
        this.ezAction = ezAction;
    }

    Map<String, Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
