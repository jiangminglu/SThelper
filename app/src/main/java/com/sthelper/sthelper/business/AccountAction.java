package com.sthelper.sthelper.business;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.view.BaseProcessDialog;

public class AccountAction extends BaseAction implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_action);
        initActionBar("个人账户");
        init();
    }

    private void init() {
        findViewById(R.id.about).setOnClickListener(this);
        findViewById(R.id.checkupdate).setOnClickListener(this);
        findViewById(R.id.callservice).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.about) {
            Intent intent = new Intent();
            intent.setClass(AccountAction.this, AboutAction.class);
            startActivity(intent);
        } else if (view.getId() == R.id.checkupdate) {
            BaseProcessDialog dialog = new BaseProcessDialog(mActivity);
            dialog.setTitle("检查版本更新");
            dialog.setMessage("正在检测新版本...");
            dialog.show();
        }else if(view.getId() == R.id.callservice){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+10086 ));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
