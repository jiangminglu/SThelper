package com.sthelper.sthelper.business.stone;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.UserApi;
import com.sthelper.sthelper.bean.StoneItemBean;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.adapter.StoneItemAdapter;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class StoneItemListAction extends BaseAction {

    public ArrayList<StoneItemBean> list;
    public ListView listView;
    public StoneItemAdapter adapter;
    public int stone_id = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stone_item_info_action);
        initActionBar("白岩石");
        stone_id = getIntent().getIntExtra("stone_id", 0);
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_stone_actionbar_bg)));
        init();
        loadData();
    }

    private void init() {
        list = new ArrayList<StoneItemBean>();
        adapter = new StoneItemAdapter(list, mActivity);
        listView = (ListView) findViewById(R.id.stone_item_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
    }

    private void loadData() {
        processDialog.show();
        UserApi api = new UserApi();
        api.getStoneChildList(stone_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (0 == response.optInt("ret")) {
                    try {
                        JsonNode node = BaseApi.mapper.readTree(response.optString("result"));
                        for (JsonNode item : node) {
                            StoneItemBean bean = BaseApi.mapper.readValue(item.toString(), StoneItemBean.class);
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

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent();
            intent.setClass(mActivity, StoneInfoAction.class);
            startActivity(intent);
        }
    };

}
