package com.sthelper.sthelper.business;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.Order;
import com.sthelper.sthelper.business.adapter.MyOrderListAdapter;

import java.util.ArrayList;

/**
 * Created by luffy on 15/7/14.
 */
public class OrderFragment extends Fragment {

    private ListView listView;
    private ArrayList<Order> list;
    private MyOrderListAdapter adapter;

    public OrderFragment() {
    }

    public static OrderFragment getInstance() {
        return new OrderFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_layout, null);
        init(view);
        return view;
    }

    private void init(View view) {

        list = new ArrayList<Order>();
        listView = (ListView) view.findViewById(R.id.order_listview);
        adapter = new MyOrderListAdapter(list, getActivity());
        listView.setAdapter(adapter);

    }
}

