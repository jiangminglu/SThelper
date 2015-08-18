package com.sthelper.sthelper.business.profile;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.bean.FavBean;
import com.sthelper.sthelper.bean.MyFavShopBean;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.adapter.MyFavAdapter;
import com.sthelper.sthelper.business.adapter.MyFavStoreAdapter;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MyFavAction extends BaseAction implements View.OnClickListener {

    private ListView listView;
    private MyFavAdapter adapter;
    MyFavStoreAdapter adapter1;
    private ArrayList<FavBean> list;
    private ArrayList<MyFavShopBean> list1;
    private TextView type1, type2, currentType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fav_action);
        initActionBar("收藏夹");
        init();
        getFavstoreList();

    }

    private void init() {

        type1 = (TextView) findViewById(R.id.fav_list_type1);
        type2 = (TextView) findViewById(R.id.fav_list_type2);
        type1.setOnClickListener(this);
        type2.setOnClickListener(this);


        listView = (ListView) findViewById(R.id.fav_listview);
    }


    @Override
    public void onClick(View view) {
        if (view == type1) {
            type1.setBackgroundResource(R.drawable.fav_type);
            type1.setTextColor(getResources().getColor(R.color.app_default_actionbar_bg));
            type2.setBackground(null);
            type2.setTextColor(Color.parseColor("#a0a0a0"));
            getFavstoreList();
        } else if (view == type2) {
            type2.setBackgroundResource(R.drawable.fav_type);
            type2.setTextColor(getResources().getColor(R.color.app_default_actionbar_bg));
            type1.setTextColor(Color.parseColor("#a0a0a0"));
            type1.setBackground(null);
            getFavGoodsList();
        }
    }

    private void getFavGoodsList() {
        int uid = SPUtil.getInt("uid");
        ShopingApi api = new ShopingApi();
        api.getFavGoodsList(uid, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (0 == response.optInt("ret")) {
                    try {
                        list = new ArrayList<FavBean>();
                        JsonNode node = BaseApi.mapper.readTree(response.getString("result"));
                        for (JsonNode item : node) {
                            FavBean bean = BaseApi.mapper.readValue(item.toString(), FavBean.class);
                            list.add(bean);
                        }
                        adapter = new MyFavAdapter(list, MyFavAction.this);
                        listView.setAdapter(adapter);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void deleteGoods(final FavBean bean) {
        processDialog.show();
        int uid = SPUtil.getInt("uid");
        ShopingApi api = new ShopingApi();
        api.delFavGoods(uid, bean.product_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (response.optInt("ret") == 0) {
                    ToastUtil.showToast("删除收藏成功");
                    list.remove(bean);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
                processDialog.dismiss();
            }
        });
    }

    public void deleteStore(final MyFavShopBean bean) {
        processDialog.show();
        int uid = SPUtil.getInt("uid");
        ShopingApi api = new ShopingApi();
        api.delFavStore(bean.shop_id, uid, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (response.optInt("ret") == 0) {
                    ToastUtil.showToast("删除收藏成功");
                    list1.remove(bean);
                    adapter1.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
                processDialog.dismiss();
            }
        });
    }

    private void getFavstoreList() {
        int uid = SPUtil.getInt("uid");
        ShopingApi api = new ShopingApi();
        api.getFavStoreList(uid, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (0 == response.optInt("ret")) {
                    try {
                        list1 = new ArrayList<MyFavShopBean>();
                        JsonNode node = BaseApi.mapper.readTree(response.getString("result"));
                        for (JsonNode item : node) {
                            MyFavShopBean bean = BaseApi.mapper.readValue(item.toString(), MyFavShopBean.class);
                            list1.add(bean);
                        }
                        adapter1 = new MyFavStoreAdapter(list1,MyFavAction.this);
                        listView.setAdapter(adapter1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

}
