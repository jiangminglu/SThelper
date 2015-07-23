package com.sthelper.sthelper.business.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.GoodsInfo;

import java.util.ArrayList;

/**
 * Created by luffy on 15/7/23.
 */
public class CarItemAdapter extends BaseAdapter {

    private ArrayList<GoodsInfo> list;
    private Activity activity;
    private LayoutInflater inflater;

    public CarItemAdapter(ArrayList<GoodsInfo> list, Activity activity) {
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
        if(view==null){
            view = inflater.inflate(R.layout.car_item_goods_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.car_item_goods_check);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.car_item_goods_img);
            viewHolder.nameTv = (TextView) view.findViewById(R.id.car_item_goods_name);
            viewHolder.priceTv = (TextView) view.findViewById(R.id.car_item_goods_price);
            viewHolder.delImg = (ImageView) view.findViewById(R.id.car_item_goods_del);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        GoodsInfo bean  = list.get(i);
        viewHolder.nameTv.setText(bean.title);
        viewHolder.priceTv.setText("￥"+bean.price);
        return view;
    }
    private static class ViewHolder{
        public CheckBox checkBox;
        public ImageView imageView;
        public TextView nameTv;
        public TextView priceTv;
        public ImageView delImg;
    }
}
