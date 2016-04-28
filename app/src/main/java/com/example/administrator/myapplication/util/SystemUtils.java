package com.example.administrator.myapplication.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import com.example.administrator.myapplication.MyApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/4/13.
 */
public class SystemUtils {

    public static String getPackageName(){
        return MyApplication.getInstance().getPackageName();
    }

    public static String getGetAssets(Context mContext,String fileName){
        StringBuilder returnString = new StringBuilder();
        AssetManager assetManager= mContext.getAssets();
        try {
            InputStream inputStream =   assetManager.open(fileName);
            inputStream.read();
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                returnString.append(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return returnString.toString();
    }


    public static void show_msg(Context context, String msg) {
        if (context == null) return;
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static int convertDpToPixel(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
