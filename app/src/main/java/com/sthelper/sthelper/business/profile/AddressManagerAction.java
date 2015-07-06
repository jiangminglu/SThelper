package com.sthelper.sthelper.business.profile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.business.BaseAction;

public class AddressManagerAction extends BaseAction {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manager_action);
        initActionBar("管理收餐地址");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_address_manager_action, menu);
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
