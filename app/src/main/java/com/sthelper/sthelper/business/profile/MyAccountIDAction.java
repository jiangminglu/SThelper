package com.sthelper.sthelper.business.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.UserApi;
import com.sthelper.sthelper.bean.UserInfo;
import com.sthelper.sthelper.business.AboutAction;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.auth.LoginAction;
import com.sthelper.sthelper.util.ImageLoadUtil;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.ToastUtil;
import com.sthelper.sthelper.view.BaseProcessDialog;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.IOException;

public class MyAccountIDAction extends BaseAction implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_action);
        initActionBar("我的账号");
        init();
        getUserInfo();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getUserInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "退出账户").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            SPUtil.clean();
            SApp.getInstance().currentUserInfo = null;
            Intent intent = new Intent();
            intent.setClass(mActivity, LoginAction.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        findViewById(R.id.address_manager_item).setOnClickListener(this);
        findViewById(R.id.bundle_tel_layout).setOnClickListener(this);
        findViewById(R.id.account_avatar).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       if (view.getId() == R.id.address_manager_item) {
            Intent intent = new Intent();
            intent.setClass(mActivity, AddressManagerAction.class);
            startActivity(intent);
        } else if (view.getId() == R.id.bundle_tel_layout) {
            Intent intent = new Intent();
            intent.setClass(mActivity, BundleNewTelAction.class);
            startActivity(intent);
        } else if (view.getId() == R.id.account_avatar) {
            Intent localIntent = new Intent();
            localIntent.setClass(mActivity, MyProfileAction.class);
            startActivity(localIntent);
        }
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
                        refreshUI();
                    } else if (response.optInt("ret") == 3001) {
                        ToastUtil.showToast(response.optString("error"));
                        Intent intent = new Intent();
                        intent.setClass(mActivity, LoginAction.class);
                        startActivity(intent);
                    } else {
                        ToastUtil.showToast(response.optString("error"));
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

    private void refreshUI() {
        ImageView avatarImg = (ImageView) findViewById(R.id.account_avatar);
        TextView nameTv = (TextView) findViewById(R.id.account_name);
        TextView slognTv = (TextView) findViewById(R.id.account_slogn);

        if (app.currentUserInfo == null) return;
        slognTv.setText("积分" + app.currentUserInfo.integral);

        nameTv.setText(app.currentUserInfo.nickname);
        ImageLoadUtil.getCircleAvatarImage(avatarImg, SApp.IMG_URL + app.currentUserInfo.face);
    }
}
