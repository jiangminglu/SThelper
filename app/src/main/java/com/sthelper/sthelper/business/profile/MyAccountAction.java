package com.sthelper.sthelper.business.profile;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.UserApi;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.util.SPUtil;

import org.apache.http.Header;
import org.json.JSONObject;

public class MyAccountAction extends BaseAction {

    private RelativeLayout totalPriceLayout;
    private TextView totalPrice;
    private ListView orderListview;

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
        totalPrice = (TextView) findViewById(R.id.total_price);
        orderListview = (ListView) findViewById(R.id.order_listview);
    }

    private void loadData() {
        int uid = SPUtil.getInt("uid");
        if (uid < 1) return;
        processDialog.show();
        UserApi api = new UserApi();
        api.getUserLatelyOrders(uid, 1, new JsonHttpResponseHandler() {
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

}
