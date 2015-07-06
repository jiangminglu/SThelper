package com.sthelper.sthelper.util;

import android.widget.Toast;

import com.sthelper.sthelper.SApp;

/**
 * Created by luffy on 15/6/30.
 */
public class ToastUtil {

    public static void showToast(String message){
        Toast.makeText(SApp.getInstance().getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
}
