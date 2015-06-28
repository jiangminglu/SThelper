package com.sthelper.sthelper;

import android.os.Bundle;

import com.sthelper.sthelper.business.BaseAction;


public class FeedBackAction extends BaseAction {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_action);
        initActionBar("意见反馈");
    }
}
