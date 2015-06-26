package com.sthelper.sthelper.business;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sthelper.sthelper.R;

public class AboutAction extends BaseAction {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_action);
        initActionBar("关于水头助手");
    }

}
