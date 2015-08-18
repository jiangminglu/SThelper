package com.sthelper.sthelper.business.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.UserApi;
import com.sthelper.sthelper.bean.AccountOrder;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.adapter.AccountOrderAdapter;
import com.sthelper.sthelper.util.SPUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyAccountAction extends BaseAction {

    private RelativeLayout totalPriceLayout;
    private TextView totalPriceTv;
    private ListView orderListview;
    private AccountOrderAdapter adapter = null;
    private List<AccountOrder> list = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_action);
        initActionBar("我的账户");
        initView();
        loadData();
    }

    private void initView() {
        totalPriceLayout = (RelativeLayout) findViewById(R.id.total_price_layout);
        totalPriceTv = (TextView) findViewById(R.id.total_price);
        orderListview = (ListView) findViewById(R.id.order_listview);
        totalPriceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mActivity, MyAllAccountAction.class);
                startActivity(intent);
            }
        });

        list = new ArrayList<AccountOrder>();
        adapter = new AccountOrderAdapter(mActivity, list);
        orderListview.setAdapter(adapter);
    }

    private void loadData() {
        processDialog.show();
        int uid = SPUtil.getInt("uid");
        if (uid < 1) return;
        processDialog.show();
        UserApi api = new UserApi();
        api.getUserLatelyOrders(uid, 1, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (response.optInt("ret") == 0) {
                    String totalPrice = response.optJSONObject("result").optString("totalprice");
                    JSONArray monthinfo = response.optJSONObject("result").optJSONArray("monthinfo");
                    try {
                        JsonNode jsonNode = BaseApi.mapper.readTree(monthinfo.toString());
                        for (JsonNode node : jsonNode) {
                            AccountOrder accountOrder = BaseApi.mapper.readValue(node.toString(), AccountOrder.class);
                            list.add(accountOrder);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if ("null".equals(totalPrice) || totalPrice == null) {
                        totalPriceTv.setText("0￥");
                    } else {
                        totalPriceTv.setText(totalPrice + "￥");
                    }
                }
                processDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
            }
        });
    }

}
