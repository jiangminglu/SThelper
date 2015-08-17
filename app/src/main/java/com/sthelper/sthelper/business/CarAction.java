package com.sthelper.sthelper.business;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.bean.CartGoodsItem;
import com.sthelper.sthelper.bean.GoodsInfo;
import com.sthelper.sthelper.business.adapter.CarItemAdapter;
import com.sthelper.sthelper.business.food.VerifyOrderAction;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CarAction extends BaseAction {


    private CarItemAdapter adapter;
    private ListView listview;
    private ArrayList<CartGoodsItem> list;

    private CheckBox allSeleck;
    public TextView allPrice;
    private TextView startOrder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_action);
        initActionBar("购物车");
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserCartGoodsList();
    }

    private void init() {
        list = new ArrayList<CartGoodsItem>();
        listview = (ListView) findViewById(R.id.car_goods_listview);
        adapter = new CarItemAdapter(list, this);
        listview.setAdapter(adapter);

        allSeleck = (CheckBox) findViewById(R.id.car_goods_select_all);
        allPrice = (TextView) findViewById(R.id.car_goods_all_price);
        startOrder = (TextView) findViewById(R.id.car_goods_start_order);


        allSeleck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                double price = 0;
                for (CartGoodsItem info : list) {
                    info.isSelect = b;
                    price = price + info.num * info.price;
                }
                if (b) {
                    allPrice.setText(price + "￥");
                } else {
                    allPrice.setText("0￥");
                }
                adapter.notifyDataSetChanged();
            }
        });
        startOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<CartGoodsItem> tempList = new ArrayList<CartGoodsItem>();
                for (int i = 0; i < list.size(); i++) {
                    CartGoodsItem item = list.get(i);
                    if (item.isSelect) {
                        tempList.add(item);
                    }
                }
                Intent intent = new Intent();
                intent.setClass(mActivity, VerifyOrderAction.class);
                intent.putParcelableArrayListExtra("list", tempList);
                startActivity(intent);
            }
        });

    }

    /**
     * 获取用户购物车列表
     */
    private void getUserCartGoodsList() {
        int uid = SPUtil.getInt("uid");
        if (uid < 1) {
            ToastUtil.showToast("请先登录");
            return;
        }
        processDialog.show();
        ShopingApi api = new ShopingApi();
        api.getCartGoodsList(uid, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (0 == response.optInt("ret")) {
                    try {
                        JsonNode root = BaseApi.mapper.readTree(response.toString());
                        JsonNode result = root.path("result");
                        JsonNode itemArray = result.get(0);
                        JsonNode tempInfo = result.get(1);
                        int totalNum = tempInfo.path("totalNum").asInt();
                        int totalPrice = tempInfo.path("totalPrice").asInt();
                        for (JsonNode item : itemArray) {
                            CartGoodsItem bean = BaseApi.mapper.readValue(item.toString(), CartGoodsItem.class);
                            list.add(bean);
                        }
                        adapter.notifyDataSetChanged();
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

    public void goodsNumOption(final int num, int goods_id) {

        int uid = SPUtil.getInt("uid");
        if (uid < 1) {
            ToastUtil.showToast("请登录");
            return;
        }
        processDialog.show();
        ShopingApi api = new ShopingApi();
        api.editCartGoodsNum(uid, goods_id, num, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
            }
        });
    }

    public void deleteGoods(final CartGoodsItem item) {

        int uid = SPUtil.getInt("uid");
        if (uid < 1) {
            ToastUtil.showToast("请登录");
            return;
        }
        processDialog.show();
        ShopingApi api = new ShopingApi();
        api.deleteCartGoods(uid, item.goods_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (0 == response.optInt("ret")) {
                    ToastUtil.showToast("删除成功");
                    list.remove(item);
                    adapter.notifyDataSetChanged();

                } else {
                    ToastUtil.showToast("删除失败");
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
