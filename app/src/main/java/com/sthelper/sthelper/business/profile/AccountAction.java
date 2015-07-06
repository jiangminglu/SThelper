package com.sthelper.sthelper.business.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.UserApi;
import com.sthelper.sthelper.bean.UserInfo;
import com.sthelper.sthelper.business.AboutAction;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.util.ImageLoadUtil;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.view.BaseProcessDialog;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.IOException;

public class AccountAction extends BaseAction implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_action);
        initActionBar("个人账户");
        init();
        getUserInfo();
    }

    private void init() {
        findViewById(R.id.about).setOnClickListener(this);
        findViewById(R.id.checkupdate).setOnClickListener(this);
        findViewById(R.id.callservice).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.about) {
            Intent intent = new Intent();
            intent.setClass(AccountAction.this, AboutAction.class);
            startActivity(intent);
        } else if (view.getId() == R.id.checkupdate) {
            BaseProcessDialog dialog = new BaseProcessDialog(mActivity);
            dialog.setTitle("检查版本更新");
            dialog.setMessage("正在检测新版本...");
            dialog.show();
        }else if(view.getId() == R.id.callservice){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+10086 ));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    private void getUserInfo(){
        int uid = SPUtil.getInt("uid");
        String token = SPUtil.getString("token");
        if(uid<1)return;
        processDialog.show();
        final UserApi api = new UserApi();
        api.getUserInfo(uid,token,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                try {
                    JsonNode node = BaseApi.mapper.readTree(response.toString());
                    if(node.path("ret").asInt() == 0){
                        UserInfo userInfo = BaseApi.mapper.readValue(node.findPath("userinfo").toString(),UserInfo.class);
                        app.currentUserInfo = userInfo;
                        refreshUI();
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
    private void refreshUI(){
        ImageView avatarImg = (ImageView) findViewById(R.id.account_avatar);
        TextView nameTv = (TextView) findViewById(R.id.account_name);
        TextView slognTv = (TextView) findViewById(R.id.account_slogn);

        if(app.currentUserInfo == null)return;
        nameTv.setText(app.currentUserInfo.nickname);
        ImageLoadUtil.getCircleAvatarImage(avatarImg,app.currentUserInfo.face);
    }
}
