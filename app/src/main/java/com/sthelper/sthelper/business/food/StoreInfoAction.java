package com.sthelper.sthelper.business.food;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.bean.FoodStoreBean;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.util.ImageLoadUtil;

public class StoreInfoAction extends BaseAction {

    private FoodStoreBean bean;
    private ImageView storeImg;
    private TextView storeNameTv;
    private RatingBar  storeRatingbar;
    private int type;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info_action);
        bean = getIntent().getParcelableExtra("bean");
        type = getIntent().getIntExtra("type", 100);
        initActionBar(bean.shop_name);
        if (type == 100) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_default_actionbar_bg)));
        } else {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_blue_actionbar_bg)));
        }
        init();
    }

    private void init(){
        storeImg= (ImageView) findViewById(R.id.store_info_img);
        storeNameTv = (TextView) findViewById(R.id.store_name);
        storeRatingbar = (RatingBar) findViewById(R.id.store_rating);

        ImageLoadUtil.getCommonImage(storeImg, SApp.IMG_URL+bean.photo);
        storeNameTv.setText(bean.shop_name);
        storeRatingbar.setRating(bean.score);
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
