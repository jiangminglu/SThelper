package com.sthelper.sthelper.business.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.bean.FoodStoreBean;
import com.sthelper.sthelper.util.ImageLoadUtil;

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
            viewHolder.logoImg = (ImageView) view.findViewById(R.id.food_store_logo);
            RatingBar ratingBar = (RatingBar) view.findViewById(R.id.food_store_listitem_rating);
            RatingBar ratingBar1 = (RatingBar) view.findViewById(R.id.food_store_listitem_rating1);
            viewHolder.timeDesc = (TextView) view.findViewById(R.id.food_store_listitem_time_desc);
            if(type == 100){
                viewHolder.ratingBar = ratingBar;
                ratingBar.setVisibility(View.VISIBLE);
                ratingBar1.setVisibility(View.GONE);
            }else if(type == 101){
                viewHolder.ratingBar = ratingBar1;
                ratingBar.setVisibility(View.GONE);
                ratingBar1.setVisibility(View.VISIBLE);
            }

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

        FoodStoreBean bean = list.get(i);
        viewHolder.nameTv.setText(bean.shop_name);
        viewHolder.ratingBar.setRating(bean.score);
        ImageLoadUtil.getCommonImage(viewHolder.logoImg, SApp.IMG_URL+bean.photo);
        viewHolder.timeDesc.setText(bean.business_time);
        if(bean.closed == 0){
            viewHolder.statusTv.setText("营业中");
        }else{
            viewHolder.statusTv.setText("暂停中");
        }
        return view;
    }

    private static class ViewHolder {
        public TextView nameTv;
        public View payView;
        public TextView statusTv;
        public TextView remarkTv;
        public RatingBar ratingBar;
        public ImageView logoImg;
        public TextView timeDesc;
    }
}
