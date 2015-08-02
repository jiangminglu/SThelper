package com.sthelper.sthelper.business.food;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.bean.FoodStoreBean;
import com.sthelper.sthelper.bean.ShopInfo;
import com.sthelper.sthelper.bean.ShopInfoBean;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.util.ImageLoadUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.IOException;

public class StoreInfoAction extends BaseAction {

    private FoodStoreBean bean;
    private ImageView storeImg;
    private TextView storeNameTv;
    private RatingBar storeRatingbar;
    private int type;
    private ShopInfoBean shopInfoBean;

    private TextView timeTv, speedTv, addressTv, priceTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info_action);
        bean = getIntent().getParcelableExtra("bean");
        type = getIntent().getIntExtra("type", 100);
        initActionBar(bean.shop_name);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        init();
        getInfo();
    }

    private void init() {
        storeImg = (ImageView) findViewById(R.id.store_info_img);
        storeNameTv = (TextView) findViewById(R.id.store_name);
        storeRatingbar = (RatingBar) findViewById(R.id.store_rating);

        timeTv = (TextView) findViewById(R.id.store_delivery_time);
        speedTv = (TextView) findViewById(R.id.store_delivery_speed);
        addressTv = (TextView) findViewById(R.id.store_delivery_address);
        priceTv = (TextView) findViewById(R.id.store_delivery_price);


        ImageLoadUtil.getCommonImage(storeImg, SApp.IMG_URL + bean.photo);
        storeNameTv.setText(bean.shop_name);
        storeRatingbar.setRating(bean.score);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    public void getInfo() {
        if (bean == null) return;
        processDialog.show();
        ShopingApi api = new ShopingApi();
        api.getShopInfoById(bean.shop_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (0 == response.optInt("ret")) {
                    JSONObject result = response.optJSONObject("result");
                    try {
                        shopInfoBean = BaseApi.mapper.readValue(result.optString("shop"), ShopInfoBean.class);
                        if (shopInfoBean != null) {
                            timeTv.setText(shopInfoBean.business_time + "");
                            speedTv.setText(shopInfoBean.send_time + "");
                            priceTv.setText(shopInfoBean.Freight + "元");
                            addressTv.setText(bean.addr);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
            }
        });
    }
}
