package com.sthelper.sthelper.business;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.FavorableBean;

import java.util.ArrayList;

public class FavorableAction extends BaseAction {

    private FAdapter adapter;
    private ArrayList<FavorableBean> list;
    private GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorable_action);
        initActionBar("优惠活动");
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7c4dff")));
        init();

        loadData();
    }

    private void init() {
        list = new ArrayList<FavorableBean>();
        adapter = new FAdapter();

        gridView = (GridView) findViewById(R.id.favorable_gridview);
        gridView.setAdapter(adapter);
    }

    private void loadData() {
        for (int i = 0; i < 20; i++) {
            FavorableBean bean = new FavorableBean();
            list.add(bean);
        }
        adapter.notifyDataSetChanged();
    }

    private class FAdapter extends BaseAdapter {

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
                view = FavorableAction.this.getLayoutInflater().inflate(R.layout.favorable_item_layout, null);
                viewHolder.imageView = (ImageView) view.findViewById(R.id.favorable_item_img);
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(app.screenW / 2, app.screenW / 2);
                view.setLayoutParams(params);
                viewHolder.titleTv = (TextView) view.findViewById(R.id.favorable_item_title);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            return view;
        }

        private class ViewHolder {
            public ImageView imageView;
            public TextView titleTv;
        }
    }
}
