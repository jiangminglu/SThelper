package com.sthelper.sthelper.business.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.bean.Address;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.adapter.AddressListAdapter;
import com.sthelper.sthelper.util.ToastUtil;

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
        listView.setOnItemLongClickListener(onItemLongClickListener);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Address address = list.get(i);
                Intent intent = new Intent();
                intent.putExtra("bean",address);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            Address item = list.get(i);
            initAlertDialog(item);
            return true;
        }
    };

    private void initAlertDialog(final Address item) {
        new AlertDialog.Builder(this).setTitle("操作").setItems(
                new String[]{"编辑", "删除"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (i == 0) {
                            Intent intent = new Intent();
                            intent.setClass(mActivity, AddAddressAction.class);
                            intent.putExtra("bean", item);
                            startActivity(intent);
                        } else if (i == 1) {
                            deleteAddress(item);
                        }
                    }
                }).show();
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
                        if(result == null)return;
                        if (result.isArray()) {
                            list.removeAll(list);
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

    private void deleteAddress(final Address item) {
        processDialog.show();
        ShopingApi api = new ShopingApi();
        api.delelteAddress(item.addr_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (response.optInt("ret") == 0) {
                    ToastUtil.showToast("删除成功");
                    list.remove(item);
                    adapter.notifyDataSetChanged();
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
