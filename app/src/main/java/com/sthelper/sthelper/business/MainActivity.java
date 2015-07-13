package com.sthelper.sthelper.business;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.capricorn.ArcMenu;
import com.fasterxml.jackson.databind.JsonNode;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.FeedBackAction;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.CommonApi;
import com.sthelper.sthelper.bean.Area;
import com.sthelper.sthelper.bean.Business;
import com.sthelper.sthelper.business.auth.LoginAction;
import com.sthelper.sthelper.business.food.FoodStoreListAction;
import com.sthelper.sthelper.business.profile.AccountAction;
import com.sthelper.sthelper.business.stone.StoneListAction;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends BaseAction {
    private static final int[] ITEM_DRAWABLES = {R.mipmap.icon_shi, R.mipmap.icon_yin, R.mipmap.icon_stone, R.mipmap.icon_hui};
    private ArcMenu goArcMenu;
    private SlidingMenu menu;

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

    }

    @Override
    protected void onResume() {
        super.onResume();
        getBusinessList();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View paramAnonymousView) {
            int id = paramAnonymousView.getId();
            switch (id) {
                case R.id.menu_user:
                    Intent localIntent = new Intent();
                    localIntent.setClass(MainActivity.this, LoginAction.class);
                    MainActivity.this.startActivity(localIntent);
                    break;
                case R.id.menu_account:
                    localIntent = new Intent();
                    localIntent.setClass(MainActivity.this, AccountAction.class);
                    MainActivity.this.startActivity(localIntent);
                    break;
                case R.id.menu_invite:
                    localIntent = new Intent();
                    localIntent.setClass(MainActivity.this, InviteAction.class);
                    MainActivity.this.startActivity(localIntent);
                    break;
                case R.id.menu_feedback:
                    localIntent = new Intent();
                    localIntent.setClass(MainActivity.this, FeedBackAction.class);
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

        this.slidLayout.findViewById(R.id.menu_user).setOnClickListener(this.onClickListener);
        this.slidLayout.findViewById(R.id.menu_account).setOnClickListener(this.onClickListener);
        this.slidLayout.findViewById(R.id.menu_invite).setOnClickListener(this.onClickListener);
        this.slidLayout.findViewById(R.id.menu_feedback).setOnClickListener(this.onClickListener);

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


}