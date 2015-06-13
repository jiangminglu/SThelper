package com.sthelper.sthelper.business.adapter;

import android.app.Activity;
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

    public FoodStoreListAdapter(ArrayList<FoodStoreBean> list, Activity mActivity) {
        this.list = list;
        this.mActivity = mActivity;
        this.inflater = mActivity.getLayoutInflater();
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
            view = inflater.inflate(R.layout.food_store_listitem_layout,null);
            viewHolder.nameTv = (TextView) view.findViewById(R.id.food_store_listitem_name);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        return  view;
    }

    private static class ViewHolder {
        public TextView nameTv;
    }
}
