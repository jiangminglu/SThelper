package com.sthelper.sthelper.business;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.UserApi;
import com.sthelper.sthelper.bean.Order;
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
    private ArrayList<Order> list;
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

        list = new ArrayList<Order>();
        listView = (ListView) view.findViewById(R.id.order_listview);
        adapter = new MyOrderListAdapter(list, getActivity());
        listView.setAdapter(adapter);

    }

    private void getOrderList() {
        int uid = SPUtil.getInt("uid");
        UserApi api = new UserApi();
        api.getUserAddressList(uid,status,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}

