package com.sthelper.sthelper.business.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.StoneItemBean;

import java.util.ArrayList;

/**
 * Created by luffy on 15/6/17.
 */
public class StoneItemAdapter extends BaseAdapter {

    private ArrayList<StoneItemBean> list;
    private Activity activity;
    private LayoutInflater inflater;

    public StoneItemAdapter(ArrayList<StoneItemBean> list, Activity activity) {
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
        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.stone_item_layout,null);
            viewHolder.titleTv = (TextView) view.findViewById(R.id.stone_item_title);
            viewHolder.priceTv = (TextView) view.findViewById(R.id.stone_item_price);
            viewHolder.priceTv1 = (TextView) view.findViewById(R.id.stone_item_price1);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        return view;
    }
    private static class ViewHolder{
        public TextView titleTv;
        public TextView priceTv;
        public TextView priceTv1;
    }
}
