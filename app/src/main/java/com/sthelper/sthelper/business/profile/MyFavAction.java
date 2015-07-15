package com.sthelper.sthelper.business.profile;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.FavBean;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.adapter.MyFavAdapter;

import java.util.ArrayList;

public class MyFavAction extends BaseAction implements View.OnClickListener {

    private ListView listView;
    private MyFavAdapter adapter;
    private ArrayList<FavBean> list;
    private TextView type1, type2, currentType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fav_action);
        initActionBar("收藏夹");
        init();

    }

    private void init() {

        type1 = (TextView) findViewById(R.id.fav_list_type1);
        type2 = (TextView) findViewById(R.id.fav_list_type2);
        type1.setOnClickListener(this);
        type2.setOnClickListener(this);

        list = new ArrayList<FavBean>();
        adapter = new MyFavAdapter(list, this);
        listView = (ListView) findViewById(R.id.fav_listview);
        listView.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        if (view == type1) {
            type1.setBackgroundResource(R.drawable.fav_type);
            type1.setTextColor(getResources().getColor(R.color.app_default_actionbar_bg));
            type2.setBackground(null);
            type2.setTextColor(Color.parseColor("#a0a0a0"));
        } else if (view == type2) {
            type2.setBackgroundResource(R.drawable.fav_type);
            type2.setTextColor(getResources().getColor(R.color.app_default_actionbar_bg));
            type1.setTextColor(Color.parseColor("#a0a0a0"));
            type1.setBackground(null);
        }
    }
}
