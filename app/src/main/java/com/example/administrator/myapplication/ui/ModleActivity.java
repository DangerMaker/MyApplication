package com.example.administrator.myapplication.ui;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ModleActivity extends BackBaseActivity implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.modle_public)
    RadioButton mModlePublic;
    @Bind(R.id.modle_private)
    RadioButton mModlePrivate;
    @Bind(R.id.modle_select_can)
    RadioButton mModleSelectCan;
    @Bind(R.id.modle_select_not)
    RadioButton mModleSelectNot;
    @Bind(R.id.modle_friend)
    RadioButton mModleFriend;
    @Bind(R.id.modle_group)
    RadioGroup mModleGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modle);
        ButterKnife.bind(this);
        setCustomTitle("选择分享范围");
        //TODO
        mModleGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.modle_public:

                break;

            case R.id.modle_private:

                break;

            case R.id.modle_friend:

                break;

            case R.id.modle_select_can:

                break;

            case R.id.modle_select_not:

                break;
        }
    }
}
