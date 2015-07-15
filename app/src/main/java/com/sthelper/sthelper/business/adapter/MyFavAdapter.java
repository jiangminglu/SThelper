package com.sthelper.sthelper.business.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.FavBean;

import java.util.ArrayList;

/**
 * Created by luffy on 15/7/15.
 */
public class MyFavAdapter extends BaseAdapter {
    private ArrayList<FavBean> list;
    private Activity activity;

    public MyFavAdapter(ArrayList<FavBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
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
            view = View.inflate(activity, R.layout.my_fav_item_layout, null);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return view;
    }

    private static class ViewHolder {

    }
}
