package com.sthelper.sthelper.business.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.Order;

import java.util.ArrayList;

/**
 * Created by luffy on 15/7/15.
 */
public class MyOrderListAdapter extends BaseAdapter {


    private ArrayList<Order> list;

    private Context context;

    public MyOrderListAdapter(ArrayList<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.order_item_layout, null);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return view;
    }

    private static class ViewHolder {

    }
}
