package com.sthelper.sthelper.business;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.view.BaseProcessDialog;

public class BaseAction extends Activity implements View.OnClickListener{
    public Activity mActivity;
    public ActionBar actionBar;
    public SApp app;
    public BaseProcessDialog processDialog;

    public void initActionBar(String title) {
        this.actionBar = getActionBar();
        if (null != this.actionBar) {
            this.actionBar.setDisplayShowHomeEnabled(false);
            this.actionBar.setDisplayShowCustomEnabled(true);
            View view = getLayoutInflater().inflate(R.layout.ui_actionbar, null);
            ((TextView) view.findViewById(R.id.title)).setText(title);
            ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
            this.actionBar.setCustomView(view, layout);
            view.findViewById(android.R.id.home).setOnClickListener(this);
        }

    }

    public void onCreate(Bundle paramBundle) {
        setTheme(R.style.actionTheme);
        this.mActivity = this;
        this.app = (SApp) getApplication();
        super.onCreate(paramBundle);
        processDialog = new BaseProcessDialog(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == android.R.id.home){
            finish();
        }
    }
}