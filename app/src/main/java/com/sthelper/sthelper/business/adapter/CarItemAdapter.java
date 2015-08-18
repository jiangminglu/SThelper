package com.sthelper.sthelper.business.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.bean.CartGoodsItem;
import com.sthelper.sthelper.business.CarAction;
import com.sthelper.sthelper.util.ImageLoadUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by luffy on 15/7/23.
 */
public class CarItemAdapter extends BaseAdapter {

    private ArrayList<CartGoodsItem> list;
    private CarAction activity;
    private LayoutInflater inflater;

    public CarItemAdapter(ArrayList<CartGoodsItem> list, CarAction activity) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.car_item_goods_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.car_item_goods_check);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.car_item_goods_img);
            viewHolder.nameTv = (TextView) view.findViewById(R.id.car_item_goods_name);
            viewHolder.priceTv = (TextView) view.findViewById(R.id.car_item_goods_total_price);
            viewHolder.delImg = (ImageView) view.findViewById(R.id.car_item_goods_del);
            viewHolder.zjImg = (ImageView) view.findViewById(R.id.car_goods_add_img);
            viewHolder.jsImg = (ImageView) view.findViewById(R.id.car_goods_del_img);
            viewHolder.numTv = (TextView) view.findViewById(R.id.car_goods_item_num);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        CartGoodsItem bean = list.get(i);


        viewHolder.nameTv.setText(bean.product_name);
        viewHolder.priceTv.setText("￥" + bean.num * bean.price);
        viewHolder.numTv.setText(bean.num + "");
        viewHolder.zjImg.setOnClickListener(new NumOptionListener(viewHolder, bean));
        viewHolder.jsImg.setOnClickListener(new NumOptionListener(viewHolder, bean));
        viewHolder.delImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.deleteGoods(list.get(i));
            }
        });
        ImageLoadUtil.getCommonImage(viewHolder.imageView, SApp.IMG_URL + bean.photo);
        viewHolder.checkBox.setOnCheckedChangeListener(new CheckListener(viewHolder, bean));
        viewHolder.checkBox.setChecked(bean.isSelect);
        return view;
    }

    private static class ViewHolder {
        public CheckBox checkBox;
        public ImageView imageView;
        public TextView nameTv;
        public TextView priceTv, numTv;
        public ImageView delImg, zjImg, jsImg;
    }

    private class NumOptionListener implements View.OnClickListener {
        ViewHolder viewHolder;
        CartGoodsItem info;

        public NumOptionListener(ViewHolder viewHolder, CartGoodsItem info) {
            this.viewHolder = viewHolder;
            this.info = info;
        }

        @Override
        public void onClick(View view) {
            int product_id = info.product_id;
            int num = info.num;
            if (view.getId() == R.id.car_goods_add_img) {//增加
                num++;
            } else if (view.getId() == R.id.car_goods_del_img) {//减少
                if (num == 0) return;
                num--;

            }
            info.num = num;
            viewHolder.numTv.setText(num + "");
            viewHolder.priceTv.setText("￥" + (num * info.price) + "");
            activity.goodsNumOption(num, product_id);
        }
    }

    private class CheckListener implements CompoundButton.OnCheckedChangeListener {

        ViewHolder viewHolder;
        CartGoodsItem info;

        public CheckListener(ViewHolder viewHolder, CartGoodsItem info) {
            this.viewHolder = viewHolder;
            this.info = info;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            info.isSelect = b;
            notifyDataSetChanged();
            double totalPrice = 0;
            for (CartGoodsItem item : list) {
                if (item.isSelect) {
                    int num = item.num;
                    totalPrice = totalPrice + num * item.price;
                }
            }
            activity.allPrice.setText("￥" + totalPrice);
        }
    }


}
