package com.example.administrator.myapplication.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.myapplication.Config;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.auth.UserService;
import com.example.administrator.myapplication.model.UserData;
import com.example.administrator.myapplication.ui.LoginActivity;
import com.example.administrator.myapplication.ui.UpdateActivity;
import com.example.administrator.myapplication.ui.view.PersonContentItemView;
import com.example.administrator.myapplication.ui.view.SelectPopupWindow;
import com.example.administrator.myapplication.util.FileUtils;
import com.example.administrator.myapplication.util.RestAdapterUtils;
import com.example.administrator.myapplication.util.SystemUtils;
import com.example.administrator.myapplication.util.UploadImageUtils;
import com.example.administrator.myapplication.zxing.android.CaptureActivity;
import com.example.administrator.myapplication.zxing.encode.CodeCreator;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-25
 */
public class PersonCenterFragment extends BaseFragment implements View.OnClickListener{

    public final static int TAG_MAIL = 0;
    public final static int TAG_NAME = 1;
    public final static int TAG_PASS = 2;
    private static final int REQUEST_CODE_SCAN = 11;

    @Bind(R.id.userinfo_photo)
    SimpleDraweeView mHeadPhoto;
    @Bind(R.id.content_username)
    TextView mUsername;
    @Bind(R.id.content_usermail)
    TextView mUsermail;

    @Bind(R.id.update_mail)
    PersonContentItemView mUpdateMail;
    @Bind(R.id.update_pass)
    PersonContentItemView mUpdatePass;
    @Bind(R.id.exit_user)
    PersonContentItemView mExiteUser;
    @Bind(R.id.center_header)
    LinearLayout header;
    @Bind(R.id.scan_two_code)
    PersonContentItemView mScanCode;
    @Bind(R.id.create_two_code)
    PersonContentItemView mCreateCode;


    UserData user = null;
    private SelectPopupWindow menuWindow;
    @Override
    public void onResume() {
        super.onResume();

        if(UserService.getInstance(getActivity()).hasActiveAccount() && Config.cookie != null){
            user = UserService.getInstance(getActivity()).getActiveAccountInfo();
            if(user != null) {
                //mHeadPhoto
                mHeadPhoto.setImageURI(Uri.parse(user.getPicture()));
                mUsername.setText("用户昵称：" + user.getName());
                mUsermail.setText("用户邮箱：" + user.getMail());
                //use test TODO
                mUsermail.setVisibility(View.VISIBLE);
                mUpdateMail.setVisibility(View.VISIBLE);
                mUpdatePass.setVisibility(View.VISIBLE);
                mExiteUser.setVisibility(View.VISIBLE);
                mCreateCode.setVisibility(View.VISIBLE);
                mScanCode.setVisibility(View.VISIBLE);
            }
        }else{
            mHeadPhoto.setImageURI(Uri.parse(""));
            mUsername.setText("未登录");
            mUsermail.setText("");
            mUsermail.setVisibility(View.GONE);
            mUpdateMail.setVisibility(View.GONE);
            mUpdatePass.setVisibility(View.GONE);
            mExiteUser.setVisibility(View.GONE);
            mCreateCode.setVisibility(View.GONE);
            mScanCode.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_person_center;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.center_header)
    public void goHeader(){
        if(!UserService.getInstance(getActivity()).hasActiveAccount() || Config.cookie == null){
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @OnClick(R.id.userinfo_photo)
    public void takePic(){
        if(!UserService.getInstance(getActivity()).hasActiveAccount() || Config.cookie == null){
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }else{
            selectPic();
        }
    }

    private void selectPic() {
        View v = getActivity().getLayoutInflater().inflate(R.layout.item_user_center_edit_avatar, null);
        menuWindow = new SelectPopupWindow(getActivity().findViewById(R.id.persion_layout), getActivity(), v);
        v.findViewById(R.id.btn_take_photo).setOnClickListener(this);
        v.findViewById(R.id.btn_pick_photo).setOnClickListener(this);
    }

    private String outputAvatarPath = "";
    public static final int IMG_FROM_PICTURE = 0x1001;
    public static final int IMG_FROM_CAMERA = 0x1002;

    private void fromPicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_FROM_PICTURE);
    }

    private void fromCamera() {
        outputAvatarPath = UploadImageUtils.getAvatarPath(getActivity());
        if(!TextUtils.isEmpty(outputAvatarPath)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File tmpFile = new File(outputAvatarPath);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tmpFile));
            startActivityForResult(intent, IMG_FROM_CAMERA);
        } else {
            SystemUtils.show_msg(getActivity(), "未插入SD卡");
        }
    }

    @OnClick({R.id.update_mail,R.id.update_pass,R.id.center_header})
    public void update(View view){
        Intent intent = new Intent(getActivity(),UpdateActivity.class);
        switch (view.getId()){
            case R.id.update_mail:
                intent.putExtra("tag",TAG_MAIL);
                startActivity(intent);
                break;
            case R.id.update_pass:
                intent.putExtra("tag",TAG_PASS);
                startActivity(intent);
                break;
            case R.id.center_header:
                break;
        }
    }


    @OnClick(R.id.scan_two_code)
    public void scanCode(){
        //TODO 扫描二维码
        Intent intent = new Intent(getActivity(),CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @OnClick(R.id.create_two_code)
    public void createCode(){
        //TODO  生成二维码
        Bitmap bitmap = CodeCreator.createQRCode(user.getName(),user.getUid(),user.getMail());
        FileUtils.saveScanBitmap(bitmap,user.getUid().toString());
        if(bitmap != null){
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_scan_code_bitmap,null);
            ImageView scanBitmap = (ImageView) view.findViewById(R.id.iv_scan_bitmap);
            scanBitmap.setImageBitmap(bitmap);
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(view);
            builder.show();
        }else {
            SystemUtils.show_msg(getActivity(),"生成二维码失败!");
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 11){
            if(resultCode == getActivity().RESULT_OK){

            }
        }
    }

    @OnClick(R.id.exit_user)
    public void exit(){
        logoutload();
    }

    private void logoutload() {
        RestAdapterUtils.getUserApi().loginOut(Config.token, Config.cookie, Config.uid, new Callback<List<String>>() {
            @Override
            public void success(List<String> list, Response response) {
                if (list != null) {
                    SystemUtils.show_msg(getActivity(), "登出成功");
                    UserService.getInstance(getActivity()).logout();
                    Config.username = null;
                }
                //登出成功,跳转到登录页面
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }

            @Override
            public void failure(RetrofitError error) {
                SystemUtils.show_msg(getActivity(), "登出失败" + error.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        menuWindow.dismiss();
        switch (view.getId()) {
            case R.id.btn_take_photo:
                fromCamera();
                break;
            case R.id.btn_pick_photo:
                fromPicture();
                break;
        }
    }
}
