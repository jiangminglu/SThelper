package com.sthelper.sthelper.business.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.Address;

import java.util.ArrayList;

/**
 * Created by luffy on 15/7/6.
 */
public class AddressListAdapter extends BaseAdapter {
    private ArrayList<Address> list;
    private Activity activity;
    private LayoutInflater inflater;

    public AddressListAdapter(ArrayList<Address> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
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
            view = inflater.inflate(R.layout.address_item_layout, null);
            viewHolder.nameTv = (TextView) view.findViewById(R.id.address_item_name);
            viewHolder.telTv = (TextView) view.findViewById(R.id.address_item_tel);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Address address = list.get(i);
        viewHolder.telTv.setText(address.mobile);
        viewHolder.nameTv.setText(address.name);

        return view;
    }

    private static class ViewHolder {
        public TextView nameTv, telTv;
    }
}
