package com.sthelper.sthelper.business.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.OrderItem;

import java.util.ArrayList;

/**
 * Created by luffy on 15/7/15.
 */
public class MyOrderListAdapter extends BaseAdapter {


    private ArrayList<OrderItem> list;
    private int status;
    private Context context;

    public MyOrderListAdapter(int status, ArrayList<OrderItem> list, Context context) {
        this.list = list;
        this.status = status;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
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
            holder.timeTv = (TextView) view.findViewById(R.id.order_create_time_tv);
            holder.orderIdTv = (TextView) view.findViewById(R.id.order_create_id_tv);
            holder.addressTv = (TextView) view.findViewById(R.id.order_address_tv);
            holder.statusTv = (TextView) view.findViewById(R.id.order_status_tv);
            holder.totalPriceTv = (TextView) view.findViewById(R.id.order_total_price_tv);
            holder.storeTv = (TextView) view.findViewById(R.id.order_item_store_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        OrderItem order = list.get(i);
        holder.storeTv.setText(order.mainInfo.shop_name);
        holder.timeTv.setText(order.mainInfo.create_time);
        holder.orderIdTv.setText("订单编号: " + order.mainInfo.order_id + "");
        holder.addressTv.setText(order.mainInfo.addr);
        String text = "";
//        @"未付款",@"待发货",@"进行中",@"已完成",@"已关闭"
        if (order.mainInfo.status == 0) {
            text = "未付款";
        } else if (order.mainInfo.status == 1) {
            text = "待发货";
        } else if (order.mainInfo.status == 2) {
            text = "进行中";
        } else if (order.mainInfo.status == 3) {
            text = "已完成";
        } else if (order.mainInfo.status == 4) {
            text = "已关闭";
        }

        if (order.mainInfo.is_daofu == 1) {
            text = "货到付款";
        }
        holder.statusTv.setText(text);
        holder.totalPriceTv.setText(order.mainInfo.total_price + "￥");
        return view;
    }

    private static class ViewHolder {
        public TextView timeTv, orderIdTv, addressTv, statusTv, totalPriceTv, storeTv;
    }
}
