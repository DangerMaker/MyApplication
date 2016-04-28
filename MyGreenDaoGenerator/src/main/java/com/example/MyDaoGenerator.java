package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "com.example.administrator.myapplication.dao");
        schema.enableKeepSectionsByDefault();

        addUser(schema);
        new DaoGenerator().generateAll(schema, "../MyApplication/app/src/main/java");
    }

    private static void addUser(Schema schema){
        Entity subscriptionItem = schema.addEntity("User");
        subscriptionItem.addStringProperty("uid").primaryKey();
        subscriptionItem.addStringProperty("uname");
        subscriptionItem.addStringProperty("nickname");
        subscriptionItem.addStringProperty("mobile");
        subscriptionItem.addStringProperty("gender");
        subscriptionItem.addStringProperty("location");
        subscriptionItem.addStringProperty("avatar");
        subscriptionItem.addStringProperty("china_coin");
    }
}
