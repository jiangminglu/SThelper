package com.sthelper.sthelper.business.stone;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.StoneItemBean;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.adapter.StoneItemAdapter;

import java.util.ArrayList;

public class StoneItemListAction extends BaseAction {

    public ArrayList<StoneItemBean> list;
    public ListView listView;
    public StoneItemAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stone_item_info_action);
        initActionBar("白岩石");
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_stone_actionbar_bg)));
        init();
        loadData();
    }

    private void init() {
        list = new ArrayList<StoneItemBean>();
        adapter = new StoneItemAdapter(list, mActivity);
        listView = (ListView) findViewById(R.id.stone_item_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
    }

    private void loadData() {
        for (int i = 0; i < 20; i++) {
            list.add(new StoneItemBean());

        }
        adapter.notifyDataSetChanged();
    }
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent();
            intent.setClass(mActivity,StoneInfoAction.class);
            startActivity(intent);
        }
    };

}
