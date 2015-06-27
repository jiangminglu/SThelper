package com.sthelper.sthelper.business;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sthelper.sthelper.R;

public class InviteAction extends BaseAction {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_action);
        initActionBar("邀请好友");
    }


}
