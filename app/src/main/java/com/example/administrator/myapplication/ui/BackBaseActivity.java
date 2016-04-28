package com.example.administrator.myapplication.ui;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.R;

public abstract class BackBaseActivity extends BaseActivity {
    private String mTitle="";
    private RelativeLayout mGoBack;

    @Override
    protected void setupToolbar() {
        if (toobar != null){
            if(!mTitle.equals("") ) {
                toobar.setTitle(mTitle);
            }
            setSupportActionBar(toobar);
            mGoBack = (RelativeLayout)toobar.findViewById(R.id.btn_go_back);
            mGoBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBack();
                }
            });
        }
    }



    public void setToolbarTitle(String title){
        mTitle=title;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    public void addFragment(Fragment newFragment,int layoutId) {
        if(!this.isFinishing()) {
            getSupportFragmentManager().beginTransaction()
                    .add(layoutId, newFragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }


    public void goBack(){
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }
    }
}
