package com.sthelper.sthelper.business.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.GoodsItemBean;

import java.util.ArrayList;

/**
 * Created by luffy on 15/6/14.
 */
public class GoodsItemAdapter extends BaseAdapter {

    private ArrayList<GoodsItemBean> list;
    private Activity activity;
    private LayoutInflater inflater;

    public GoodsItemAdapter(ArrayList<GoodsItemBean> list, Activity activity) {
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
            view = inflater.inflate(R.layout.goods_item_layout, null);
            viewHolder.nameTv = (TextView) view.findViewById(R.id.goods_item_name);
            viewHolder.priceTv = (TextView) view.findViewById(R.id.goods_item_price);
            viewHolder.addImg = (ImageView) view.findViewById(R.id.goods_item_add);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }


        return view;
    }

    private static class ViewHolder {
        public TextView nameTv;
        public TextView priceTv;
        public ImageView addImg;
    }
}
