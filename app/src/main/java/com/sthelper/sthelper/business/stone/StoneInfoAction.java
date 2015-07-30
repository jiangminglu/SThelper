package com.sthelper.sthelper.business.stone;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.StoneItemBean;
import com.sthelper.sthelper.business.BaseAction;
import com.viewpagerindicator.CirclePageIndicator;

public class StoneInfoAction extends BaseAction {


    ViewPager viewpager;
    CirclePageIndicator indicator;
    TextView itemname;
    TextView itemaddress;
    TextView itemprice;
    RelativeLayout infolayout2;
    TextView stonestorename;
    TextView stonestoreremark;
    TextView stonestoreaddress;
    TextView itemteltv;
    private StoneItemBean bean;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stone_info_action);
        bean = getIntent().getParcelableExtra("bean");
        initActionBar(bean.my_stone_name);
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_stone_actionbar_bg)));
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stone_info_action, menu);
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

    private void initView() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        itemname = (TextView) findViewById(R.id.item_name);
        itemaddress = (TextView) findViewById(R.id.item_address);
        itemprice = (TextView) findViewById(R.id.item_price);
        infolayout2 = (RelativeLayout) findViewById(R.id.info_layout2);
        stonestorename = (TextView) findViewById(R.id.stone_store_name);
        stonestoreremark = (TextView) findViewById(R.id.stone_store_remark);
        stonestoreaddress = (TextView) findViewById(R.id.stone_store_address);
        itemteltv = (TextView) findViewById(R.id.item_tel_tv);
        itemname.setText(bean.my_stone_name);
        itemaddress.setText(bean.adress);
        itemprice.setText("￥" + bean.price);

        stonestorename.setText(bean.shop_name);
        stonestoreremark.setText(bean.adress);
        stonestoreaddress.setText(bean.tips);

        itemteltv.setText("TEL:" + bean.tel);
        itemteltv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:" + bean.tel);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });
        findViewById(R.id.item_call_tel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:" + bean.tel);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });
    }

}
