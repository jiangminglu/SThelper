package com.sthelper.sthelper.business.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.business.BaseAction;

public class AddAddressAction extends BaseAction {

    private EditText nameEt, telEt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_action);
        initActionBar("添加收餐人信息");
        init();
    }

    private void init() {
        nameEt = (EditText) findViewById(R.id.add_address_name);
        telEt = (EditText) findViewById(R.id.add_address_tel);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_address_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
