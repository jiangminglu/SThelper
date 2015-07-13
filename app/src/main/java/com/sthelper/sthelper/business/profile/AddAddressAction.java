package com.sthelper.sthelper.business.profile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

public class AddAddressAction extends BaseAction {

    private EditText nameEt, telEt, usernameEt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_action);
        initActionBar("添加收餐人信息");
        init();
    }

    private void init() {
        nameEt = (EditText) findViewById(R.id.add_address_name);
        telEt = (EditText) findViewById(R.id.add_address_tel);
        usernameEt = (EditText) findViewById(R.id.add_address_username);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_address_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            addAddress();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addAddress() {
        processDialog.show();
        String userename = usernameEt.getText().toString();
        String mobile = telEt.getText().toString();
        String address = nameEt.getText().toString();
        ShopingApi api = new ShopingApi();
        api.addAddressItem(userename, mobile, address, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                String error = response.optString("error");
                int ret = response.optInt("ret");
                ToastUtil.showToast(error);
                if(ret == 0){
                    finish();
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
