package com.sthelper.sthelper.business.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.bean.AccountOrder;
import com.sthelper.sthelper.util.ImageLoadUtil;

public class AccountOrderAdapter extends BaseAdapter {

    private List<AccountOrder> list = new ArrayList<AccountOrder>();

    private Context context;
    private LayoutInflater layoutInflater;

    public AccountOrderAdapter(Context context, List<AccountOrder> list) {
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.account_order_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.accountOrderImg = (ImageView) convertView.findViewById(R.id.account_order_img);
            viewHolder.accountOrderNameTv = (TextView) convertView.findViewById(R.id.account_order_name_tv);
            viewHolder.accountOrderTimeTv = (TextView) convertView.findViewById(R.id.account_order_time_tv);
            viewHolder.accountOrderPriceTv = (TextView) convertView.findViewById(R.id.account_order_price_tv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AccountOrder order = list.get(position);
        ImageLoadUtil.getCommonImage(viewHolder.accountOrderImg, SApp.IMG_URL+order.photo);
        viewHolder.accountOrderNameTv.setText(order.shop_name+"    订单编号: "+order.order_code);
        viewHolder.accountOrderTimeTv.setText(order.create_time);
        viewHolder.accountOrderPriceTv.setText("-"+order.total_price+"");
        return convertView;
    }

    protected class ViewHolder {
        private ImageView accountOrderImg;
        private TextView accountOrderNameTv;
        private TextView accountOrderTimeTv;
        private TextView accountOrderPriceTv;
    }
}
