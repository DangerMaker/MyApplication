package com.example.administrator.myapplication.model;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-18
 */
public class LoginSuc {

    /**
     * sessid : TUBWt3KAMPhK1CuYv2yfUIKBzUUawNwdD1I1Y-sBKpo
     * session_name : SESS868e69c66eea1f5855bb00860e7c84f9
     * token : IqpU1K--D_U800_AJ3uxmHpMHRWusMBKpjG5wODrMpw
     * user : {"uid":"1","name":"NATURE","mail":"dongdong19892011@163.com","theme":"","signature":"","signature_format":null,"created":"1459819913","access":"1459845860","login":1459847043,"status":"1","timezone":"Asia/Shanghai","language":"","picture":null,"init":"dongdong19892011@163.com","data":false,"roles":{"2":"authenticated user","3":"administrator"},"rdf_mapping":{"rdftype":["sioc:UserAccount"],"name":{"predicates":["foaf:name"]},"homepage":{"predicates":["foaf:page"],"type":"rel"}}}
     */

    private String sessid;
    private String session_name;
    private String token;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSessid() {
        return sessid;
    }

    public void setSessid(String sessid) {
        this.sessid = sessid;
    }

    public String getSession_name() {
        return session_name;
    }

    public void setSession_name(String session_name) {
        this.session_name = session_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
