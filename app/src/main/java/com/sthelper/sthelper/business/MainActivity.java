package com.sthelper.sthelper.business;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;

import com.capricorn.ArcMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.business.auth.LoginAction;
import com.sthelper.sthelper.business.food.FoodStoreListAction;

public class MainActivity extends BaseAction {
    private static final int[] ITEM_DRAWABLES = {R.mipmap.icon_shi, R.mipmap.icon_yin, R.mipmap.icon_stone, R.mipmap.icon_hui};
    public float density;
    private ArcMenu goArcMenu;
    private SlidingMenu menu;
    public int screenH = 0;
    public int screenW = 0;
    private View slidLayout;

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        initScreenWH();
        this.menu = new SlidingMenu(this);
        this.menu.setMode(SlidingMenu.LEFT);
        this.menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        this.menu.setShadowWidth(0);
        this.menu.setShadowDrawable(null);
        this.menu.setBehindOffset(this.screenW / 2);
        this.menu.attachToActivity(this, SlidingMenu.LEFT);
        this.menu.setFadeEnabled(true);
        this.menu.setFadeDegree(0.5F);
        this.slidLayout = getLayoutInflater().inflate(R.layout.slide_menu, null);
        this.menu.setMenu(this.slidLayout);
        init();
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
                case 100:
                    Intent intent = new Intent();
                    intent.setClass(mActivity, FoodStoreListAction.class);
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

        this.slidLayout.findViewById(R.id.menu_user).setOnClickListener(this.onClickListener);

    }

    private void initScreenWH() {
        Display display = getWindowManager().getDefaultDisplay();
        Point localPoint = new Point();
        display.getSize(localPoint);
        this.screenH = localPoint.y;
        this.screenW = localPoint.x;
        this.density = getResources().getDisplayMetrics().density;
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