package com.example.administrator.myapplication.model.map;

import com.example.administrator.myapplication.model.User;
import com.example.administrator.myapplication.model.UserData;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-25
 */
public class User2UserData {
    public static UserData transfrom(User user){
        UserData userData = new UserData();
        userData.setUid(user.getUid());
        userData.setName(user.getName());
        userData.setMail(user.getMail());
        userData.setTheme(user.getTheme());
        userData.setSignature(user.getSignature());
        userData.setSignature_format(user.getSignature_format());
        userData.setCreated(user.getCreated());
        userData.setAccess(user.getAccess());
        userData.setLanguage(user.getLanguage());
        if(user.getPicture() != null) {
            userData.setPicture(user.getPicture().getUrl());
        }else{
            userData.setPicture("picture");
        }
        return userData;
    }
}
