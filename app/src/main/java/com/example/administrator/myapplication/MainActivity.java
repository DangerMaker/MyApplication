package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.administrator.myapplication.ui.BaseActivity;
import com.example.administrator.myapplication.ui.LoginActivity;
import com.example.administrator.myapplication.ui.NewsGridActivity;
import com.example.administrator.myapplication.ui.NewsListActivity;
import com.example.administrator.myapplication.ui.PersonCenterActivity;
import com.example.administrator.myapplication.ui.RegisterActivity;
import com.example.administrator.myapplication.ui.adapter.MenuListAdapter;
import com.example.administrator.myapplication.ui.adapter.NewsGridAdapter;

import java.util.Arrays;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements MenuListAdapter.OnListClick{

    @Bind(R.id.list)
    ListView mListView;

    MenuListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setCustomTitle("首页");
        adapter = new MenuListAdapter(this,this);
        mListView.setAdapter(adapter);

        initData();
    }

    String[] items = {"ListView","GridView","Register","Login And Logout","PersonalCenter"};
    private void initData() {
        adapter.addItems(Arrays.asList(items));
    }

    @Override
    public void click(String str) {
        if(str.equals(items[0])) {
            startActivity(new Intent(this, NewsListActivity.class));
        }else if(str.equals(items[1])){
            startActivity(new Intent(this, NewsGridActivity.class));
        }else if(str.equals(items[2])){
            startActivity(new Intent(this, RegisterActivity.class));
        }else if(str.equals(items[3])){
            startActivity(new Intent(this,LoginActivity.class));
        }else if(str.equals(items[4])){
            startActivity(new Intent(this, PersonCenterActivity.class));
        }
    }
}
