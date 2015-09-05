package com.sthelper.sthelper.business;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.capricorn.ArcMenu;
import com.fasterxml.jackson.databind.JsonNode;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.CommonApi;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.api.UserApi;
import com.sthelper.sthelper.bean.Area;
import com.sthelper.sthelper.bean.Business;
import com.sthelper.sthelper.bean.UserInfo;
import com.sthelper.sthelper.business.auth.LoginAction;
import com.sthelper.sthelper.business.food.FoodStoreListAction;
import com.sthelper.sthelper.business.food.OpenStoreAction;
import com.sthelper.sthelper.business.food.StoreInfoAction;
import com.sthelper.sthelper.business.profile.MyAccountAction;
import com.sthelper.sthelper.business.profile.MyAccountIDAction;
import com.sthelper.sthelper.business.profile.MyFavAction;
import com.sthelper.sthelper.business.profile.MyOrderListAction;
import com.sthelper.sthelper.business.stone.StoneInfoAction;
import com.sthelper.sthelper.business.stone.StoneListAction;
import com.sthelper.sthelper.util.ImageLoadUtil;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends BaseAction {
    private static final int[] ITEM_DRAWABLES = {R.mipmap.icon_shi, R.mipmap.icon_yin, R.mipmap.icon_stone, R.mipmap.icon_hui};
    private ArcMenu goArcMenu;
    private SlidingMenu menu;
    private ImageView img1, img2, img3, img4;

    private View slidLayout;

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        getActionBar().hide();

        this.menu = new SlidingMenu(this);
        this.menu.setMode(SlidingMenu.LEFT);
        this.menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        this.menu.setShadowWidth(0);
        this.menu.setShadowDrawable(null);
        this.menu.setBehindOffset(app.screenW / 2);
        this.menu.attachToActivity(this, SlidingMenu.LEFT);
        this.menu.setFadeEnabled(true);
        this.menu.setFadeDegree(0.5F);
        this.slidLayout = getLayoutInflater().inflate(R.layout.slide_menu, null);
        this.menu.setMenu(this.slidLayout);
        init();

        savaAppLogo();
        getMainPic();
        getUserInfo();

    }


    @Override
    protected void onResume() {
        super.onResume();
        getOpenShopStatus();
        getBusinessList();
    }

    private void savaAppLogo() {
        Bitmap photo = BitmapFactory.decodeResource(getResources(), R.mipmap.app_logo);
        File f = new File(app.appLogo);
        try {
            if (f.exists()) return;
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f);
            photo.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View paramAnonymousView) {
            int id = paramAnonymousView.getId();
            int uid = SPUtil.getInt("uid");
            switch (id) {
                case R.id.menu_user:
                    Intent localIntent = new Intent();
                    if (uid < 1) {
                        localIntent.setClass(MainActivity.this, LoginAction.class);
                    } else {
                        localIntent.setClass(MainActivity.this, MyAccountIDAction.class);
                    }
                    MainActivity.this.startActivity(localIntent);
                    break;
                case R.id.menu_order:
                    localIntent = new Intent();
                    if (uid < 1) {
                        localIntent.setClass(MainActivity.this, LoginAction.class);
                    } else {
                        localIntent.setClass(MainActivity.this, MyOrderListAction.class);
                    }
                    MainActivity.this.startActivity(localIntent);
                    break;
                case R.id.menu_account:
                    localIntent = new Intent();
                    if (uid < 1) {
                        localIntent.setClass(MainActivity.this, LoginAction.class);
                    } else {
                        localIntent.setClass(MainActivity.this, MyAccountAction.class);
                    }
                    MainActivity.this.startActivity(localIntent);

                    break;
                case R.id.menu_invite:
                    localIntent = new Intent();
                    localIntent.setClass(MainActivity.this, InviteAction.class);
                    MainActivity.this.startActivity(localIntent);
                    break;
                case R.id.menu_fav:

                    if (uid < 1) {
                        localIntent = new Intent();
                        localIntent.setClass(MainActivity.this, LoginAction.class);
                        MainActivity.this.startActivity(localIntent);
                        break;
                    }
                    localIntent = new Intent();
                    localIntent.setClass(MainActivity.this, MyFavAction.class);
                    MainActivity.this.startActivity(localIntent);
                    break;
                case 100:
                    goArcMenu.resetBgView();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent();
                            intent.putExtra("type", 100);
                            intent.setClass(mActivity, FoodStoreListAction.class);
                            startActivity(intent);
                        }
                    }, 150);
                    break;
                case 101:
                    goArcMenu.resetBgView();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent();
                            intent.putExtra("type", 101);
                            intent.setClass(mActivity, FoodStoreListAction.class);
                            startActivity(intent);
                        }
                    }, 150);

                    break;
                case 102:
                    goArcMenu.resetBgView();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent();
                            intent.setClass(mActivity, StoneListAction.class);
                            startActivity(intent);
                        }
                    }, 200);

                    break;
                case 103:
                    goArcMenu.resetBgView();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent();
                            intent.setClass(mActivity, FavorableAction.class);
                            startActivity(intent);
                        }
                    }, 200);
                    break;
                case R.id.menu_setting:
                    Intent intent = new Intent();
                    intent.setClass(mActivity, SettingAction.class);
                    startActivity(intent);
                    break;
                case R.id.menu_open_store:
                    if (uid < 1) {
                        intent = new Intent();
                        intent.setClass(mActivity, LoginAction.class);
                        startActivity(intent);
                        break;
                    }
                    Object tag = paramAnonymousView.getTag();
                    intent = new Intent();
                    if (tag == null) {
                        intent.setClass(mActivity, OpenStoreAction.class);
                    } else {
                        try {
                            int shop_id = Integer.parseInt(tag + "");
                            intent.setClass(mActivity, StoreInfoAction.class);
                            intent.putExtra("shop_id", shop_id);
                        } catch (Exception e) {
                            intent.setClass(mActivity, OpenStoreAction.class);
                            intent.putExtra("status", tag + "");
                        }
                    }
                    startActivity(intent);
                    break;
            }
        }
    };


    private void init() {
        this.goArcMenu = ((ArcMenu) findViewById(R.id.main_item_go));
        int i = ITEM_DRAWABLES.length;
        for (int j = 0; j < i; j++) {
            ImageView localImageView = new ImageView(this);
            localImageView.setId(100 + j);
            localImageView.setImageResource(ITEM_DRAWABLES[j]);
            this.goArcMenu.addItem(localImageView, onClickListener);
        }

        img1 = (ImageView) findViewById(R.id.main_item_img1);
        img2 = (ImageView) findViewById(R.id.main_item_img2);
        img3 = (ImageView) findViewById(R.id.main_item_img3);
        img4 = (ImageView) findViewById(R.id.main_item_img4);
        this.slidLayout.findViewById(R.id.menu_user).setOnClickListener(this.onClickListener);
        this.slidLayout.findViewById(R.id.menu_account).setOnClickListener(this.onClickListener);
        this.slidLayout.findViewById(R.id.menu_invite).setOnClickListener(this.onClickListener);
        this.slidLayout.findViewById(R.id.menu_order).setOnClickListener(this.onClickListener);
        this.slidLayout.findViewById(R.id.menu_fav).setOnClickListener(this.onClickListener);
        this.slidLayout.findViewById(R.id.menu_setting).setOnClickListener(this.onClickListener);
        this.slidLayout.findViewById(R.id.menu_open_store).setOnClickListener(this.onClickListener);

        TextView textView = (TextView) findViewById(R.id.user_option);
        int uid = SPUtil.getInt("uid");
        if (uid > 0) {
            textView.setText("用户中心");
        } else {
            textView.setText("用户登录");
        }
    }

    private void getBusinessList() {
        CommonApi api = new CommonApi();
        api.getBusinessList(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JsonNode jsonNode = BaseApi.mapper.readTree(response.toString());
                    if (jsonNode.path("ret").asInt() == 0) {
                        JsonNode businessNode = jsonNode.findParent("business");
                        if (businessNode == null) return;
                        Business busines = new Business();
                        busines.area_id = businessNode.findValue("area_id").asInt();
                        busines.area_name = businessNode.findValue("area_name").asText();
                        busines.is_hot = businessNode.findValue("is_hot").asInt();
                        busines.area_id = businessNode.findValue("area_id").asInt();
                        busines.orderby = businessNode.findValue("orderby").asInt();

                        app.business = busines;
                        JsonNode areaNode = jsonNode.findParent("area");
                        if (areaNode == null) return;
                        Area area = new Area();
                        area.area_id = areaNode.findValue("area_id").asInt();
                        area.area_name = areaNode.findValue("area_name").asText();
                        area.orderby = areaNode.findValue("orderby").asInt();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void onBackPressed() {
        if (this.goArcMenu.isExpanded()) {
            this.goArcMenu.resetBgView();
            return;
        }
        if (this.menu.isMenuShowing()) {
            this.menu.showContent();
            return;
        }
        super.onBackPressed();
    }

    private void getMainPic() {
        CommonApi api = new CommonApi();
        api.getMainPic(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (response.optInt("ret") == 0) {
                    JSONArray array = response.optJSONArray("result");
                    if (array != null && array.length() > 0) {
                        try {
                            JSONObject jsonObject1 = array.getJSONObject(0);
                            String url1 = jsonObject1.optString("photo");
                            ImageLoadUtil.getCommonImage(img1, SApp.IMG_URL + url1);

                            JSONObject jsonObject2 = array.getJSONObject(1);
                            String url2 = jsonObject2.optString("photo");
                            ImageLoadUtil.getCommonImage(img2, SApp.IMG_URL + url2);

                            JSONObject jsonObject3 = array.getJSONObject(2);
                            String url3 = jsonObject3.optString("photo");
                            ImageLoadUtil.getCommonImage(img3, SApp.IMG_URL + url3);

                            JSONObject jsonObject4 = array.getJSONObject(3);
                            String url4 = jsonObject4.optString("photo");
                            ImageLoadUtil.getCommonImage(img4, SApp.IMG_URL + url4);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void getOpenShopStatus() {
        int uid = SPUtil.getInt("uid");
        if (uid < 1) return;
        ShopingApi api = new ShopingApi();
        api.getShopStatus(uid, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
//                {"ret":1,"result":{"shop_id":"1"}}
                View view = slidLayout.findViewById(R.id.menu_open_store);
                TextView textView = (TextView) view.findViewById(R.id.menu_open_store_tv);
                int ret = response.optInt("ret");
                if (ret == 1) {//审核成功
                    int shop_id = response.optJSONObject("result").optInt("shop_id");
                    view.setTag(shop_id);
                    textView.setText("我的店铺");
                } else if (ret == 0) {//正在审核中或审核失败
                    view.setTag("正在审核中或审核失败");
                    textView.setText("我要开店");
                } else if (ret == 2) {//尚未开店
                    view.setTag(null);
                    textView.setText("我要开店");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void getUserInfo() {
        int uid = SPUtil.getInt("uid");
        String token = SPUtil.getString("token");
        if (uid < 1) return;
        processDialog.show();
        final UserApi api = new UserApi();
        api.getUserInfo(uid, token, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                try {
                    JsonNode node = BaseApi.mapper.readTree(response.toString());

                    if (node.path("ret").asInt() == 0) {
                        UserInfo userInfo = BaseApi.mapper.readValue(node.findPath("userinfo").toString(), UserInfo.class);
                        app.currentUserInfo = userInfo;
                    } else {
                        ToastUtil.showToast(response.optString("error"));
                        SPUtil.save("uid", -10);
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

}