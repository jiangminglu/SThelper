package com.sthelper.sthelper.business.food;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import com.sthelper.sthelper.bean.ShopInfo;
import com.sthelper.sthelper.bean.ShopInfoBean;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.adapter.CommentItemLayoutAdapter;
import com.sthelper.sthelper.business.auth.LoginAction;
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
    boolean isFav = false;
    MenuItem menuItem = null;
    View headView = null;
    ListView listView = null;
    private TextView timeTv, speedTv, addressTv, priceTv, telTv, commentCountTv;
    CommentItemLayoutAdapter adapter = null;
    int shop_id = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = new ListView(mActivity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(params);
        setContentView(listView);

        headView = getLayoutInflater().inflate(R.layout.activity_store_info_action, null);
        listView.addHeaderView(headView);
        bean = getIntent().getParcelableExtra("bean");
        shop_id = getIntent().getIntExtra("shop_id", 0);
        type = getIntent().getIntExtra("type", 100);
        if(bean!=null){
            shop_id = bean.shop_id;
        }
        getInfo();
        isFav();
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
//        storeRatingbar.setRating(shopInfoBean);

        adapter = new CommentItemLayoutAdapter(mActivity, comments);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_store_info_action, menu);
        menuItem = menu.findItem(R.id.action_settings);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_settings);
        if (isFav) {
            menuItem.setIcon(getResources().getDrawable(R.mipmap.shop_faved));
        } else {
            menuItem.setIcon(getResources().getDrawable(R.mipmap.shop_fav));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            if (isFav) {
                delFav();
            } else {
                addFav();
            }
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
                            actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
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
                                if(comments.size()>0){
                                    commentCountTv.setText("(共"+comments.size()+"评论)");
                                }else{
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

    public void isFav() {
        int uid = SPUtil.getInt("uid");
        if (uid < 1) {
            Intent intent = new Intent();
            intent.setClass(mActivity, LoginAction.class);
            startActivity(intent);
            return;
        }
        ShopingApi api = new ShopingApi();
        api.isFavStore(uid, shop_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (response.optInt("ret") == 0) {
                    isFav = true;
                } else {
                    isFav = false;
                }
                invalidateOptionsMenu();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void addFav() {

        int uid = SPUtil.getInt("uid");
        if (uid < 1) {
            Intent intent = new Intent();
            intent.setClass(mActivity, LoginAction.class);
            startActivity(intent);
            return;
        }
        processDialog.show();
        ShopingApi api = new ShopingApi();
        api.addFavStore(bean.shop_id, uid, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (response.optInt("ret") == 0) {
                    isFav = true;
                    invalidateOptionsMenu();
                    ToastUtil.showToast("收藏成功");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
            }
        });
    }

    private void delFav() {
        int uid = SPUtil.getInt("uid");
        if (uid < 1) {
            Intent intent = new Intent();
            intent.setClass(mActivity, LoginAction.class);
            startActivity(intent);
            return;
        }
        processDialog.show();
        ShopingApi api = new ShopingApi();
        api.delFavStore(bean.shop_id, uid, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (response.optInt("ret") == 0) {
                    isFav = false;
                    invalidateOptionsMenu();
                    ToastUtil.showToast("取消收藏成功");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
            }
        });
    }


}
