package com.example.administrator.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myapplication.Config;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.auth.UserService;
import com.example.administrator.myapplication.model.AlterMailSuc;
import com.example.administrator.myapplication.model.AlterPassSuc;
import com.example.administrator.myapplication.ui.fragment.PersonCenterFragment;
import com.example.administrator.myapplication.util.RestAdapterUtils;
import com.example.administrator.myapplication.util.SystemUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by shand on 2016/4/21.
 */
public class UpdateActivity extends BackBaseActivity {

    @Bind(R.id.update_mail)
    EditText mUpdateMail;

    int tag = -1;
    String temp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        tag = getIntent().getIntExtra("tag", -1);
        if (tag == -1) {
            finish();
            return;
        } else if (tag == PersonCenterFragment.TAG_MAIL) {
            setCustomTitle("修改邮箱");
        } else if(tag == PersonCenterFragment.TAG_PASS){
            setCustomTitle("修改密码");
        }

    }

    @OnClick(R.id.confirm_update)
    public void confirmUpdate() {
        String update = mUpdateMail.getText().toString().trim();
//        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
//        Pattern regex = Pattern.compile(check);
//        Matcher matcher = regex.matcher(mail);
//        boolean isMatched = matcher.matches();
//        if (TextUtils.isEmpty(mail) && !isMatched) {
//            SystemUtils.show_msg(this, "密码或邮箱不能为空");
//            return;
//        }
        String password = UserService.getInstance(this).getPassword();
        if(tag == PersonCenterFragment.TAG_MAIL){
            updateMail(update, password);
        }else if(tag == PersonCenterFragment.TAG_PASS){
            updatePass(update, password);
            temp = password;
        }


    }

    public void updateMail(String mail, String password) {
        RestAdapterUtils.getUserApi().alterMail(Config.token, Config.cookie, Config.uid,
                mail, password, new Callback<AlterMailSuc>() {
                    @Override
                    public void success(AlterMailSuc alterMailSuc, Response response) {
                        UserService.getInstance(UpdateActivity.this).updateAccoutMail(alterMailSuc.getMail());
                        SystemUtils.show_msg(UpdateActivity.this, "修改成功");
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        SystemUtils.show_msg(UpdateActivity.this, "修改失败" + error.getMessage());
                    }
                });
    }

    public void updatePass(String pass, String password) {
        RestAdapterUtils.getUserApi().alterPass(Config.token, Config.cookie, Config.uid,
                pass, password, new Callback<AlterPassSuc>() {
                    @Override
                    public void success(AlterPassSuc alterPassSuc, Response response) {
                        UserService.getInstance(UpdateActivity.this).setPassword(temp);
                        SystemUtils.show_msg(UpdateActivity.this, "修改成功");
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        SystemUtils.show_msg(UpdateActivity.this, "修改失败" + error.getMessage());
                    }
                });
    }
}
