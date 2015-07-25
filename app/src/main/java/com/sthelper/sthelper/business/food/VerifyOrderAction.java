package com.sthelper.sthelper.business.food;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.bean.Address;
import com.sthelper.sthelper.bean.GoodsInfo;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.profile.AddAddressAction;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class VerifyOrderAction extends BaseAction {

    private LinearLayout goodsListView;
    private TextView addAddressTv;//添加收货地址
    private View addressLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_order_action);
        initActionBar("确认订单");
        init();
    }

    private void init() {
        goodsListView = (LinearLayout) findViewById(R.id.order_goods_listview);

        addAddressTv = (TextView) findViewById(R.id.add_address);
        addressLayout = findViewById(R.id.address_layout);
        goodsListView.removeAllViews();
        initGoodsMenu();
        initAddress();
    }

    private void initGoodsMenu() {
        Set<Integer> set = app.carGoodsMap.keySet();
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()) {
            int key = iterator.next();
            GoodsInfo info = app.carGoodsMap.get(key);
            View view = getLayoutInflater().inflate(R.layout.order_goods_item_layout, null);
            TextView nameTv = (TextView) view.findViewById(R.id.order_goods_item_name);
            TextView numTv = (TextView) view.findViewById(R.id.order_goods_item_num);
            TextView priceTv = (TextView) view.findViewById(R.id.order_goods_item_price);

            nameTv.setText(info.title);
            numTv.setText(info.num + "");
            priceTv.setText(info.price + "");
            goodsListView.addView(view);
        }

    }

    private void initAddress() {
        getAddressListData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getAddressListData();
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

    private void getAddressListData() {
        processDialog.show();
        ShopingApi api = new ShopingApi();
        api.getAddressList(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                try {
                    JsonNode jsonNode = BaseApi.mapper.readTree(response.toString());
                    if (jsonNode.get("ret").asInt() == 0) {
                        ArrayList<Address> list = new ArrayList<Address>();
                        JsonNode result = jsonNode.get("result");
                        if (result.isArray()) {
                            list.removeAll(list);
                            for (JsonNode item : result) {
                                Address address = BaseApi.mapper.readValue(item.toString(), Address.class);
                                list.add(address);
                            }
                        }
                        if (list.size() == 0) {
                            addressLayout.setVisibility(View.GONE);
                            addAddressTv.setVisibility(View.VISIBLE);
                            addAddressTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent();
                                    intent.setClass(mActivity, AddAddressAction.class);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            addressLayout.setVisibility(View.VISIBLE);
                            addAddressTv.setVisibility(View.GONE);
                            Address address = list.get(0);
                            TextView nameTv = (TextView) addressLayout.findViewById(R.id.address_item_name);
                            TextView telTv = (TextView) addressLayout.findViewById(R.id.address_item_tel);
                            nameTv.setText(address.name);
                            telTv.setText(address.mobile);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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
