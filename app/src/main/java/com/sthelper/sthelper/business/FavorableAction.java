package com.sthelper.sthelper.business;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.bean.FavorableBean;

import java.util.ArrayList;

public class FavorableAction extends BaseAction {

    private FAdapter adapter;
    private ArrayList<FavorableBean> list;
    private GridView gridView;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorable_action);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7c4dff")));
        initActionBar("优惠活动");
        init();

        loadData();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.webview);
        webView.setVisibility(View.VISIBLE);
        // 支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 控制网页的链接仍在webView中跳转
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);   //在当前的webview中跳转到新的url
                return true;
            }
        });
        webView.loadUrl("http://120.26.49.208/yiyuanduobao/");


        list = new ArrayList<FavorableBean>();
        adapter = new FAdapter();

        gridView = (GridView) findViewById(R.id.favorable_gridview);
        gridView.setAdapter(adapter);
    }

    private void loadData() {
        for (int i = 0; i < 20; i++) {
            FavorableBean bean = new FavorableBean();
            list.add(bean);
        }
        adapter.notifyDataSetChanged();
    }

    private class FAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = FavorableAction.this.getLayoutInflater().inflate(R.layout.favorable_item_layout, null);
                viewHolder.imageView = (ImageView) view.findViewById(R.id.favorable_item_img);
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(app.screenW / 2, app.screenW / 2);
                view.setLayoutParams(params);
                viewHolder.titleTv = (TextView) view.findViewById(R.id.favorable_item_title);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            return view;
        }

        private class ViewHolder {
            public ImageView imageView;
            public TextView titleTv;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }
}
