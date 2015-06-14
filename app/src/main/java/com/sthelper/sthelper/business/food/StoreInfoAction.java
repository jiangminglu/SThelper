package com.sthelper.sthelper.business.food;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.FoodStoreBean;
import com.sthelper.sthelper.business.BaseAction;

public class StoreInfoAction extends BaseAction {

    private FoodStoreBean bean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info_action);
        bean = (FoodStoreBean) getIntent().getSerializableExtra("bean");
        initActionBar("老川菜");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store_info_action, menu);
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
