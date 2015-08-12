package com.sthelper.sthelper.business;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.UserApi;
import com.sthelper.sthelper.bean.Order;
import com.sthelper.sthelper.bean.OrderItem;
import com.sthelper.sthelper.business.adapter.MyOrderListAdapter;
import com.sthelper.sthelper.util.SPUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by luffy on 15/7/14.
 */
public class OrderFragment extends Fragment {

    private ListView listView;
    private ArrayList<OrderItem> list;
    private MyOrderListAdapter adapter;
    private int status;

    public OrderFragment() {
    }

    public static OrderFragment getInstance(int status) {
        OrderFragment fragment = new OrderFragment();
        fragment.status = status;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_layout, null);
        init(view);
        getOrderList();
        return view;
    }

    private void init(View view) {

        list = new ArrayList<OrderItem>();
        listView = (ListView) view.findViewById(R.id.order_listview);
        listView.setOnItemClickListener(onItemClickListener);
        adapter = new MyOrderListAdapter(status, list, getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), OrderInfoAction.class);
                intent.putExtra("bean", list.get(i));
                startActivity(intent);
            }
        });

    }

    private void getOrderList() {
        int uid = SPUtil.getInt("uid");
        UserApi api = new UserApi();
        api.getUserAddressList(uid, status, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JsonNode node = BaseApi.mapper.readTree(response.toString());
                    if (0 == node.path("ret").asInt()) {
                        JsonNode result = node.path("result");
                        for (JsonNode resultItem : result) {

                            OrderItem order = BaseApi.mapper.readValue(resultItem.toString(), OrderItem.class);
                            list.add(order);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            OrderItem item = list.get(i);
            Intent intent = new Intent();
            intent.setClass(getActivity(), OrderInfoAction.class);
            intent.putExtra("bean", item);
            startActivityForResult(intent,1024);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            OrderItem bean = data.getParcelableExtra("bean");
            if(bean!=null){
                OrderItem temp = null;
                for(OrderItem item:list){
                    if(item.mainInfo.order_id == bean.mainInfo.order_id){
                        temp = item;
                        break;
                    }
                }
                if(temp!=null){
                    list.remove(temp);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}

