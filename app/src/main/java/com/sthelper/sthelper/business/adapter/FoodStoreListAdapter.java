package com.sthelper.sthelper.business.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.FoodStoreBean;

import java.util.ArrayList;

/**
 * Created by luffy on 15/6/13.
 */
public class FoodStoreListAdapter extends BaseAdapter {

    private ArrayList<FoodStoreBean> list;
    private Activity mActivity;
    private LayoutInflater inflater;
    private int type;

    public FoodStoreListAdapter(ArrayList<FoodStoreBean> list, Activity mActivity, int type) {
        this.list = list;
        this.mActivity = mActivity;
        this.inflater = mActivity.getLayoutInflater();
        this.type = type;
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
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.food_store_listitem_layout, null);
            viewHolder.nameTv = (TextView) view.findViewById(R.id.food_store_listitem_name);
            viewHolder.payView = view.findViewById(R.id.food_store_pay_view);
            viewHolder.statusTv = (TextView) view.findViewById(R.id.food_store_listitem_status);
            viewHolder.remarkTv = (TextView) view.findViewById(R.id.food_store_listitem_time_remark);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (type == 100) {
            viewHolder.remarkTv.setTextColor(Color.parseColor("#e5863c"));
            viewHolder.statusTv.setTextColor(Color.parseColor("#e5863c"));
            viewHolder.payView.setBackgroundResource(R.mipmap.icon_pay_online);
        } else if (type == 101) {
            viewHolder.payView.setBackgroundResource(R.mipmap.icon_pay_online_blue);
            viewHolder.statusTv.setTextColor(Color.parseColor("#00b290"));
            viewHolder.remarkTv.setTextColor(Color.parseColor("#00b290"));
        }
        return view;
    }

    private static class ViewHolder {
        public TextView nameTv;
        public View payView;
        public TextView statusTv;
        public TextView remarkTv;
    }
}
