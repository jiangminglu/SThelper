package com.sthelper.sthelper.business.food;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.bean.Comment;
import com.sthelper.sthelper.bean.FoodStoreBean;
import com.sthelper.sthelper.bean.ShopInfoBean;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.adapter.CommentItemLayoutAdapter;
import com.sthelper.sthelper.util.ImageLoadUtil;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoreInfoAction extends BaseAction {

    private FoodStoreBean bean;
    private ImageView storeImg;
    private TextView storeNameTv;
    private RatingBar storeRatingbar;
    private int type;
    private ShopInfoBean shopInfoBean;
    private List<Comment> comments = new ArrayList<Comment>();
    MenuItem menuItem = null;
    View headView = null;
    ListView listView = null;
    boolean isMy = false;
    boolean isOpen = false;
    private TextView timeTv, speedTv, addressTv, priceTv, telTv, commentCountTv;
    CommentItemLayoutAdapter adapter = null;
    int shop_id = 0;
    int currentType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = new ListView(mActivity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(params);
        setContentView(listView);
        bean = getIntent().getParcelableExtra("bean");
        shop_id = getIntent().getIntExtra("shop_id", 0);
        type = getIntent().getIntExtra("type", 100);
        isMy = getIntent().getBooleanExtra("isMy", false);
        currentType = getIntent().getIntExtra("type", 100);
        if (bean != null)
            initActionBar(bean.shop_name);
        else
            initActionBar("");
        if (currentType == FoodStoreListAction.TYPE_SHI) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_default_actionbar_bg)));
        } else {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_blue_actionbar_bg)));
        }
        headView = getLayoutInflater().inflate(R.layout.activity_store_info_action, null);
        listView.addHeaderView(headView);

        if (bean != null) {
            shop_id = bean.shop_id;
        }
        getInfo();
    }

    private void init() {
        storeImg = (ImageView) headView.findViewById(R.id.store_info_img);
        storeNameTv = (TextView) headView.findViewById(R.id.store_name);
        storeRatingbar = (RatingBar) headView.findViewById(R.id.store_rating);
        telTv = (TextView) headView.findViewById(R.id.store_tel);
        commentCountTv = (TextView) headView.findViewById(R.id.store_comments_count);
        timeTv = (TextView) headView.findViewById(R.id.store_delivery_time);
        speedTv = (TextView) headView.findViewById(R.id.store_delivery_speed);
        addressTv = (TextView) headView.findViewById(R.id.store_delivery_address);
        priceTv = (TextView) headView.findViewById(R.id.store_delivery_price);


        ImageLoadUtil.getCommonImage(storeImg, SApp.IMG_URL + shopInfoBean.photo);
        storeNameTv.setText(shopInfoBean.shop_name);

        adapter = new CommentItemLayoutAdapter(mActivity, comments);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isMy){
            getMenuInflater().inflate(R.menu.menu_store_info_action, menu);
            menuItem = menu.findItem(R.id.action_settings);
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(!isMy)return super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.action_settings);
        if (isOpen) {
            menuItem.setTitle("打烊");
        } else {
            menuItem.setTitle("营业");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(!isMy)return false;
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            shopOption(!isOpen);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getInfo() {
        processDialog.show();
        ShopingApi api = new ShopingApi();
        api.getShopInfoById(shop_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (0 == response.optInt("ret")) {
                    JSONObject result = response.optJSONObject("result");
                    try {
                        shopInfoBean = BaseApi.mapper.readValue(result.optString("shop"), ShopInfoBean.class);
                        if (shopInfoBean != null) {
                            initActionBar(shopInfoBean.shop_name);
                            if (currentType == FoodStoreListAction.TYPE_SHI) {
                                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_default_actionbar_bg)));
                            } else {
                                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_blue_actionbar_bg)));
                            }
                            init();
                            timeTv.setText(shopInfoBean.business_time + "");
                            speedTv.setText(shopInfoBean.send_time + "");
                            priceTv.setText(shopInfoBean.freight + "元");
                            telTv.setText("" + shopInfoBean.tel);
                            addressTv.setText(shopInfoBean.addr);
                            storeRatingbar.setRating(shopInfoBean.score);

                            JsonNode commentsNode = BaseApi.mapper.readTree(result.optString("comments"));
                            if (commentsNode.isArray()) {
                                for (JsonNode item : commentsNode) {
                                    Comment comment = BaseApi.mapper.readValue(item.toString(), Comment.class);
                                    comments.add(comment);
                                }
                                if (comments.size() > 0) {
                                    commentCountTv.setText("(共" + comments.size() + "评论)");
                                } else {
                                    commentCountTv.setText("(暂无评论)");
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
            }
        });
    }

    public void shopOption(final boolean flag) {
        ShopingApi api = new ShopingApi();
        int uid = SPUtil.getInt("uid");
        api.shopOption(uid, shop_id, flag, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (response.optInt("ret") == 0) {
                    if (flag) {
                        ToastUtil.showToast("已经营业");
                    } else {
                        ToastUtil.showToast("已经打烊");
                    }
                    isOpen = flag;
                    invalidateOptionsMenu();
                } else {
                    ToastUtil.showToast("操作失败");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

}
