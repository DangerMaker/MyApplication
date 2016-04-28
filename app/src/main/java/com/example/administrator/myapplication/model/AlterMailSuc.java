package com.example.administrator.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-25
 */
public class AlterMailSuc {

    /**
     * mail : liuxiansheng@qq.com
     * current_pass : 22222222
     * uid : 27
     * roles : {"2":"authenticated user"}
     */

    private String mail;
    private String current_pass;
    private String uid;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCurrent_pass() {
        return current_pass;
    }

    public void setCurrent_pass(String current_pass) {
        this.current_pass = current_pass;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
