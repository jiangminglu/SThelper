package com.sthelper.sthelper.business.food;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.FoodApi;
import com.sthelper.sthelper.bean.FoodStoreBean;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.adapter.FoodStoreListAdapter;
import com.sthelper.sthelper.util.ToastUtil;
import com.sthelper.sthelper.view.SListView;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class HotStoreAction extends BaseAction {

    private ListView listView;
    private ArrayList<FoodStoreBean> list;
    private FoodStoreListAdapter adapter;
    int currentType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_store_action);
        initActionBar("热门店铺");
        currentType = getIntent().getIntExtra("type", 100);
        if (currentType == FoodStoreListAction.TYPE_SHI) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_default_actionbar_bg)));
        } else {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_blue_actionbar_bg)));
        }

        list = new ArrayList<FoodStoreBean>();
        adapter = new FoodStoreListAdapter(list, mActivity, currentType);
        listView = (ListView) findViewById(R.id.hot_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
        loadData();
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent();
            intent.setClass(mActivity, TakingOrderAction.class);
            intent.putExtra("bean", list.get(i));
            intent.putExtra("type", currentType);
            startActivity(intent);
        }
    };

    private void loadData() {
        processDialog.show();
        int cateId = 100;
        if (currentType == FoodStoreListAction.TYPE_SHI) {
            cateId = 53;
        } else {
            cateId = 71;
        }
        FoodApi api = new FoodApi();
        api.getFoodStoreList(1, "hot",null, cateId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                try {
                    JsonNode node = BaseApi.mapper.readTree(response.toString());
                    if (0 == node.path("ret").asInt()) {
                        JsonNode result = node.path("result");
                        if (result.isArray()) {
                            Gson gson = new Gson();
                            for (JsonNode item : result) {
                                FoodStoreBean bean = gson.fromJson(item.toString(), FoodStoreBean.class);
                                list.add(bean);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        String error = node.path("result").textValue();
                        ToastUtil.showToast(error);
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
