package com.sthelper.sthelper.business.profile;

import android.os.Bundle;
import android.widget.ListView;

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

public class MyAllAccountAction extends BaseAction {

    private ListView orderListview;
    private AccountOrderAdapter adapter = null;
    private List<AccountOrder> list = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_all_account_action);
        initActionBar("全部账单");

        orderListview = (ListView) findViewById(R.id.all_listview);
        list = new ArrayList<AccountOrder>();
        adapter = new AccountOrderAdapter(mActivity, list);
        orderListview.setAdapter(adapter);
        loadData();
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
