package com.sthelper.sthelper.business.food;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.FoodApi;
import com.sthelper.sthelper.bean.FoodStoreBean;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.CarAction;
import com.sthelper.sthelper.business.adapter.FoodStoreListAdapter;
import com.sthelper.sthelper.business.profile.MyOrderListAction;
import com.sthelper.sthelper.util.ToastUtil;
import com.sthelper.sthelper.view.SListView;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 食物列表界面
 */
public class FoodStoreListAction extends BaseAction {


    public static int TYPE_SHI = 100;
    public static int TYPE_YIN = 101;

    public int currentType = 100;
    private SListView listView;
    private ArrayList<FoodStoreBean> list;
    private FoodStoreListAdapter adapter;

    private ImageView hotImg;
    private View bottomLayout;
    private PopupWindow typePop, flavorPop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_store_list_action);
        initActionBar("店铺列表");

        currentType = getIntent().getIntExtra("type", 100);
        if (currentType == TYPE_SHI) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_default_actionbar_bg)));
        } else {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_blue_actionbar_bg)));
        }
        init();
        loadData();
    }

    private void init() {
        initTypePop();
        list = new ArrayList<FoodStoreBean>();

        adapter = new FoodStoreListAdapter(list, mActivity, currentType);
        listView = (SListView) findViewById(R.id.food_store_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);

        hotImg = (ImageView) findViewById(R.id.food_store_hot_img);
        bottomLayout = findViewById(R.id.food_store_bottom_layout);
        if (currentType == TYPE_SHI) {
            hotImg.setBackgroundResource(R.mipmap.icon_store_hot);
            bottomLayout.setBackgroundColor(getResources().getColor(R.color.app_default_actionbar_bg));
        } else {
            hotImg.setBackgroundResource(R.mipmap.icon_store_hot_blue);
            bottomLayout.setBackgroundColor(getResources().getColor(R.color.app_blue_actionbar_bg));
        }
        findViewById(R.id.food_store_all_type).setOnClickListener(onClickListener);
        findViewById(R.id.goto_car).setOnClickListener(onClickListener);
        findViewById(R.id.goto_order).setOnClickListener(onClickListener);
    }

    private void initTypePop() {
        final LinearLayout rootView = (LinearLayout) getLayoutInflater().inflate(R.layout.food_pop_layout, null);
        typePop = new PopupWindow(rootView, app.screenW, getResources().getDimensionPixelSize(R.dimen.food_store_h));
        typePop.setOutsideTouchable(true);
        final int count = rootView.getChildCount();
        for (int i = 0; i < count; i++) {
            RelativeLayout item = (RelativeLayout) rootView.getChildAt(i);
            if (currentType == 100) {
                item.getChildAt(1).setBackgroundResource(R.mipmap.icon_check_yellow);
            } else if (currentType == 101) {
                item.getChildAt(1).setBackgroundResource(R.mipmap.icon_check_blue);
            }

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < count; j++) {
                        RelativeLayout item = (RelativeLayout) rootView.getChildAt(j);
                        item.getChildAt(1).setVisibility(View.GONE);
                    }
                    View imageView = ((RelativeLayout) (view)).getChildAt(1);
                    imageView.setVisibility(View.VISIBLE);
                    typePop.dismiss();
                }
            });
        }
    }

    private void loadData() {
        processDialog.show();
        int cateId;
        if (currentType == TYPE_SHI) {
            cateId = 53;
        } else {
            cateId = 71;
        }
        FoodApi api = new FoodApi();
        api.getFoodStoreList(1, cateId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                try {
                    JsonNode node = BaseApi.mapper.readTree(response.toString());
                    if (0 == node.path("ret").asInt()) {
                        JsonNode result = node.path("result");
                        if (result.isArray()) {
                            Gson gson = new Gson();
                            for (JsonNode item : result) {
                                FoodStoreBean bean = gson.fromJson(item.toString(), FoodStoreBean.class);
                                list.add(bean);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        String error = node.path("result").textValue();
                        ToastUtil.showToast(error);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_food_store_list_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(mActivity, FindAction.class);
            startActivity(intent);
            return true;
        }
        if (id == android.R.id.home) {
            if (typePop != null && typePop.isShowing()) {
                typePop.dismiss();
                return true;
            }
            if (flavorPop != null && flavorPop.isShowing()) {
                flavorPop.dismiss();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.food_store_all_type) {
                if (typePop == null) initTypePop();
                if (typePop.isShowing()) {
                    typePop.dismiss();
                } else {
                    typePop.showAsDropDown(view);
                }
            } else if (view.getId() == R.id.goto_car) {
                Intent intent = new Intent();
                intent.setClass(mActivity, CarAction.class);
                startActivity(intent);
            } else if (view.getId() == R.id.goto_order) {
                Intent intent = new Intent();
                intent.setClass(mActivity, MyOrderListAction.class);
                startActivity(intent);
            }
        }
    };
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent();
            intent.setClass(mActivity, TakingOrderAction.class);
            intent.putExtra("bean", list.get(i));
            intent.putExtra("type", currentType);
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed() {
        if (typePop != null && typePop.isShowing()) {
            typePop.dismiss();
            return;
        }
        if (flavorPop != null && flavorPop.isShowing()) {
            flavorPop.dismiss();
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {


        super.onDestroy();
    }
}
