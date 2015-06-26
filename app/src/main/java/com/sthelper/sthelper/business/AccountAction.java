package com.sthelper.sthelper.business;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sthelper.sthelper.R;

public class AccountAction extends BaseAction implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_action);
        initActionBar("个人账户");
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.about){
            Intent intent = new Intent();
            intent.setClass(AccountAction.this,AboutAction.class);
            startActivity(intent);
        }
    }
}
