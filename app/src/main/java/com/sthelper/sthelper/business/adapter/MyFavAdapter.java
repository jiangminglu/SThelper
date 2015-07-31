package com.sthelper.sthelper.business.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.bean.FavBean;
import com.sthelper.sthelper.business.profile.MyFavAction;
import com.sthelper.sthelper.util.ImageLoadUtil;

import java.util.ArrayList;

/**
 * Created by luffy on 15/7/15.
 */
public class MyFavAdapter extends BaseAdapter {
    private ArrayList<FavBean> list;
    private MyFavAction activity;

    public MyFavAdapter(ArrayList<FavBean> list, MyFavAction activity) {
        this.list = list;
        this.activity = activity;
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
            view = View.inflate(activity, R.layout.my_fav_item_layout, null);
            holder.delImg = (ImageView) view.findViewById(R.id.item_del);
            holder.imageView = (ImageView) view.findViewById(R.id.item_img);
            holder.itemNameTv = (TextView) view.findViewById(R.id.item_name);
            holder.ratingBar = (RatingBar) view.findViewById(R.id.item_rating);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final  FavBean bean = list.get(i);
        ImageLoadUtil.getCommonImage(holder.imageView, SApp.IMG_URL+bean.photo);
        holder.itemNameTv.setText(bean.product_name);
        holder.delImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.deleteGoods(bean);
            }
        });
        return view;
    }

    private static class ViewHolder {
        public ImageView imageView,delImg;
        public TextView itemNameTv;
        public RatingBar ratingBar;
    }
}
