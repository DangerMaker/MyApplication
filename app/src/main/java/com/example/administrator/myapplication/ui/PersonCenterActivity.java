package com.example.administrator.myapplication.ui;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.myapplication.Config;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.ui.view.PersonContentItemView;
import com.example.administrator.myapplication.util.RestAdapterUtils;
import com.example.administrator.myapplication.util.SystemUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by Administrator on 2016/4/15.
 */
public class PersonCenterActivity extends BackBaseActivity {


    @Bind(R.id.userinfo_photo)
    SimpleDraweeView mHeadPhoto;
    @Bind(R.id.content_username)
    TextView mUsername;
    @Bind(R.id.content_usermail)
    TextView mUsermail;
    @Bind(R.id.content)
    TextView content;
private boolean b = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        ButterKnife.bind(this);
        setCustomTitle("个人中心");
        loadData();
    }


    @OnClick(R.id.userinfo_photo)
    public void serHeadImage(){
        if(b){
            AssetManager manager = this.getAssets();
            try {
                InputStream input = manager.open("sun.png");
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                GenericDraweeHierarchy hierarchy = mHeadPhoto.getHierarchy();
                hierarchy.setPlaceholderImage(new BitmapDrawable(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");//相片类型
            startActivityForResult(intent,1);
        }
    }


    public void loadData() {
        RestAdapterUtils.getUserStringApi().getPersonalData(Config.token, Config.cookie, Config.uid, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                System.out.println(s);
                content.setText(s);
            }

            @Override
            public void failure(RetrofitError error) {
                SystemUtils.show_msg(PersonCenterActivity.this,"获取个人信息失败"+error.getMessage());
            }
        });
    }

//    private void initView(PersonContent content) {
//        String url = content.getPicture().getUrl();
//        Uri uri = Uri.parse(url);
//        mHeadPhoto.setImageURI(uri);
//        mUsername.setText(content.getName());
//        mUsermail.setText(content.getMail());
//    }

    private void initData(String s) {

    }

    @OnClick(R.id.exit_user)
    public void loginOut(){
        //loginOut
        logoutload();
    }

    @OnClick(R.id.update_mail)
    public void updatePerson(){
        //Update
        update();
    }
    /**
     * 设置个人信息
     * */
    private void update() {
        startActivity(new Intent(this,UpdateActivity.class));
    }

    /**
    * 登出
    * */
    private void logoutload() {
        RestAdapterUtils.getUserApi().loginOut(Config.token, Config.cookie, Config.uid, new Callback<List<String>>() {
            @Override
            public void success(List<String> list, Response response) {
                if (list != null) {
                    SystemUtils.show_msg(PersonCenterActivity.this, "登出成功");
                }
                //登出成功,跳转到登录页面
                startActivity(new Intent(PersonCenterActivity.this,LoginActivity.class));
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                SystemUtils.show_msg(PersonCenterActivity.this, "登出失败" + error.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == 1) {
                Uri originalUri = data.getData();        //获得图片的uri
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(column_index);
                File file = new File(path);
                System.out.println(file.getName());
                upImage(file);
        }
    }

    private void upImage(File file) {
        TypedFile filed = new TypedFile("application/octet-stream",file);
        RestAdapterUtils.getUserApi().upFile(Config.token, Config.cookie,file.getName(),filed, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                SystemUtils.show_msg(PersonCenterActivity.this,"上传成功");
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.getMessage());
                SystemUtils.show_msg(PersonCenterActivity.this,"上传失败"+error.getMessage());
            }
        });
    }
}


