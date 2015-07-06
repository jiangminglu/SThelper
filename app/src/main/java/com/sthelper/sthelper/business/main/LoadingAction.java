package com.sthelper.sthelper.business.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.CommonApi;
import com.sthelper.sthelper.business.MainActivity;
import com.sthelper.sthelper.util.SPUtil;

import org.apache.http.Header;
import org.json.JSONObject;

public class LoadingAction extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_action);
        init();
    }

    private void init() {
        CommonApi api = new CommonApi();
        api.getAreaList(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                initAreaList(response);
                goMain();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                goMain();
            }
        });
    }

    private void initAreaList(JSONObject response) {

        if (response.optInt("ret") == 0) {
            JSONObject result = response.optJSONObject("result");
            JSONObject one = result.optJSONObject("1");
            int orderby = one.optInt("orderby");
            int area_id = one.optInt("area_id");
            String area_name = one.optString("area_name");
            SPUtil.save("orderby", orderby);
            SPUtil.save("area_id", area_id);
            SPUtil.save("area_name", area_name);
        }

    }

    private void goMain() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

}
