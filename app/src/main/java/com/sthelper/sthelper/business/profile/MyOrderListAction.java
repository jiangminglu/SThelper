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

    private static String[] TITLES = {"全部订单", "进行成功", "交易中", "交易失败"};

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_order_list_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }else if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private class OrderPageAdapter extends FragmentPagerAdapter {
        public OrderPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return OrderFragment.getInstance();
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
    }
}
