package com.sthelper.sthelper.business.food;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.FoodStoreBean;
import com.sthelper.sthelper.bean.GoodsItemBean;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.adapter.GoodsItemAdapter;
import com.sthelper.sthelper.view.SListView;

import java.util.ArrayList;

import static com.sthelper.sthelper.R.id.store_rating;

/**
 * 下订单点菜
 */
public class TakingOrderAction extends BaseAction {


    private FoodStoreBean bean;
    private ImageView storeImg;
    private RatingBar storeRate;
    private TextView storeNameTv;
    private LinearLayout storeGoodsListContent;
    private SListView rightListView;
    private ArrayList<GoodsItemBean> list;
    private GoodsItemAdapter adapter = null;

    private Dialog detailDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taking_order_action);
        initActionBar("老川菜");
        bean = (FoodStoreBean) getIntent().getSerializableExtra("bean");
        init();
        loadData();
    }

    private void init() {

        list = new ArrayList<GoodsItemBean>();

        storeImg = (ImageView) findViewById(R.id.store_pic);
        storeNameTv = (TextView) findViewById(R.id.store_name);
        storeRate = (RatingBar) findViewById(store_rating);
        storeGoodsListContent = (LinearLayout) findViewById(R.id.store_goods_content);
        rightListView = (SListView) findViewById(R.id.store_goods_item_listview);
        adapter = new GoodsItemAdapter(list, mActivity);
        rightListView.setAdapter(adapter);
        rightListView.setOnItemClickListener(onItemClickListener);
        storeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mActivity, StoreInfoAction.class);
                startActivity(intent);
            }
        });
    }

    private void initDialog() {
        detailDialog = new Dialog(mActivity, R.style.full_dialog);
        detailDialog.setContentView(R.layout.goods_item_detail_layout);
    }

    private void loadData() {

        for (int i = 0; i < 20; i++) {
            GoodsItemBean bean = new GoodsItemBean();
            list.add(bean);
        }
        adapter.notifyDataSetChanged();


        for (int i = 0; i < 8; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(mActivity);
            textView.setPadding(0, 24, 0, 24);
            textView.setText("大饼");
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(16);
            textView.setTextColor(Color.BLACK);
            textView.setLayoutParams(params);
            storeGoodsListContent.addView(textView);
            textView.setOnClickListener(onClickListener);
        }

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int count = storeGoodsListContent.getChildCount();
            for (int i = 0; i < count; i++) {
                TextView item = (TextView) storeGoodsListContent.getChildAt(i);
                item.setBackgroundResource(R.drawable.goods_item_normal);
                item.setTextColor(Color.BLACK);
            }

            TextView textView = (TextView) view;
            textView.setBackgroundResource(R.drawable.goods_item_pressed);
            textView.setTextColor(Color.parseColor("#d9681d"));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_taking_order_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (detailDialog == null) {
                initDialog();
            }
            detailDialog.show();
        }
    };
}
