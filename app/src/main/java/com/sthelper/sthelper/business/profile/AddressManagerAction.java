package com.sthelper.sthelper.business.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.bean.Address;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.adapter.AddressListAdapter;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class AddressManagerAction extends BaseAction {

    private AddressListAdapter adapter;
    private ArrayList<Address> list;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manager_action);
        initActionBar("管理收餐地址");
        init();
        getListData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getListData();
    }

    private void init() {
        listView = (ListView) findViewById(R.id.address_listview);
        list = new ArrayList<Address>();
        adapter = new AddressListAdapter(list, this);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_address_manager_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(mActivity, AddAddressAction.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getListData() {
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
                        JsonNode result = jsonNode.get("result");
                        if (result.isArray()) {
                            for (JsonNode item : result) {
                                Address address = BaseApi.mapper.readValue(item.toString(), Address.class);
                                list.add(address);
                            }
                            adapter.notifyDataSetChanged();
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
