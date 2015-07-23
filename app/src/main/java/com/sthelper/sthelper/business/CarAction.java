package com.sthelper.sthelper.business;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.GoodsInfo;
import com.sthelper.sthelper.business.adapter.CarItemAdapter;

import java.util.ArrayList;

public class CarAction extends BaseAction {


    private CarItemAdapter adapter;
    private ListView listview;
    private ArrayList<GoodsInfo> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_action);
        initActionBar("购物车");
        init();
    }

    private void init() {
        list = getIntent().getParcelableArrayListExtra("list");
        listview = (ListView) findViewById(R.id.car_goods_listview);
        adapter = new CarItemAdapter(list, this);
        listview.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_car_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
