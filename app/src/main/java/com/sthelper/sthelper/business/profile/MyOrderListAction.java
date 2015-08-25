package com.sthelper.sthelper.business.profile;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.business.OrderFragment;
import com.viewpagerindicator.TabPageIndicator;

public class MyOrderListAction extends FragmentActivity {

    private static String[] TITLES = {"全部", "未付款", "待发货", "进行中", "已完成", "已关闭"};

    private TabPageIndicator indicator;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.actionTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list_action);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setTitle("我的订单");
        init();
    }

    private void init() {
        indicator = (TabPageIndicator) findViewById(R.id.indicator);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        OrderPageAdapter adapter = new OrderPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
    }

    private class OrderPageAdapter extends FragmentPagerAdapter {
        public OrderPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return OrderFragment.getInstance(i-1);
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
    }
}
