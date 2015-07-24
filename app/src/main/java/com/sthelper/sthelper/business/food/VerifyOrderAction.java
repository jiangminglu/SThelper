package com.sthelper.sthelper.business.food;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.business.BaseAction;

public class VerifyOrderAction extends BaseAction {

    private LinearLayout goodsListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_order_action);
        initActionBar("确认订单");
        init();
    }

    private void init(){
        goodsListView = (LinearLayout) findViewById(R.id.order_goods_listview);
        goodsListView.removeAllViews();
        for(int i=0; i<4;i++){
            View view  = getLayoutInflater().inflate(R.layout.order_goods_item_layout,null);
            goodsListView.addView(view);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_verify_order_action, menu);
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
