package com.sthelper.sthelper.business.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.OrderItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by luffy on 15/7/15.
 */
public class MyOrderListAdapter extends BaseAdapter {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
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
            holder.tipsTv = (TextView) view.findViewById(R.id.order_tips_tv);
            holder.statusTv = (TextView) view.findViewById(R.id.order_status_tv);
            holder.totalPriceTv = (TextView) view.findViewById(R.id.order_total_price_tv);
            holder.storeTv = (TextView) view.findViewById(R.id.order_item_store_name);
            holder.payMethodTv = (TextView) view.findViewById(R.id.order_pay_method_tv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        OrderItem order = list.get(i);
        holder.storeTv.setText(order.mainInfo.shop_name);
        try {
            Date date = new Date(order.mainInfo.create_time * 1000);
            String time = format.format(date);
            holder.timeTv.setText("下单时间:    " + time);
        } catch (Exception e) {

        }

        holder.orderIdTv.setText("订单编号:    " + order.mainInfo.order_code + "");
        holder.tipsTv.setText("订单备注:    "+order.mainInfo.tips);
        String text = "";
//        @"未付款",@"待发货",@"进行中",@"已完成",@"已关闭"
        if (order.mainInfo.status == 0) {
            text = "未付款";
        } else if (order.mainInfo.status == 1) {
            text = "待发货";
        } else if (order.mainInfo.status == 2) {
            text = "送餐中";
        } else if (order.mainInfo.status == 3) {
            text = "已完成";
        } else if (order.mainInfo.status == 4) {
            text = "已关闭";
        }
        holder.payMethodTv.setText("支付方式:    支付宝");
        if (order.mainInfo.is_daofu == 1) {
            text = "线下付款";
            holder.payMethodTv.setText("支付方式:    " + text);
        }
        holder.statusTv.setText(text);
        holder.totalPriceTv.setText(order.mainInfo.total_price + "元");
        return view;
    }

    private static class ViewHolder {
        public TextView timeTv, orderIdTv, tipsTv, statusTv, totalPriceTv, storeTv, payMethodTv;
    }
}
