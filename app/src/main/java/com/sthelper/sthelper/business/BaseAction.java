package com.sthelper.sthelper.business;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.sthelper.sthelper.R;

public class BaseAction extends Activity {
    public Activity mActivity;
    public ActionBar actionBar;

    private void initActionBar() {
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
    }

    public void initActionBar(String title) {
        initActionBar();
        actionBar.setTitle(title);
    }

    public void onCreate(Bundle paramBundle) {
        setTheme(R.style.actionTheme);
        this.mActivity = this;
        super.onCreate(paramBundle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}