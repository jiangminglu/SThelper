package com.sthelper.sthelper.business.profile;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.AuthApi;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.auth.LoginAction;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

public class ChangePwdAction extends BaseAction {

    private EditText oldPwdEt, newPwdEt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd_action);
        initActionBar("修改密码");

        oldPwdEt = (EditText) findViewById(R.id.old_pwd_et);
        newPwdEt = (EditText) findViewById(R.id.new_pwd_et);
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPwd = oldPwdEt.getText().toString();
                String newPwd = newPwdEt.getText().toString();
                processDialog.show();
                int uid = SPUtil.getInt("uid");
                AuthApi authApi = new AuthApi();
                authApi.changePassword(uid, oldPwd, newPwd, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        processDialog.dismiss();
                        if (0 == response.optInt("ret")) {
                            ToastUtil.showToast("修改密码成功，请重新登录");
                            SPUtil.clean();
                            Intent intent = new Intent();
                            intent.setClass(mActivity, LoginAction.class);
                            startActivity(intent);
                            finish();
                        } else {
                            String error = response.optString("error");
                            ToastUtil.showToast(error);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        processDialog.dismiss();
                    }
                });
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(0, 1, 0, "确定").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {

        }
        return super.onOptionsItemSelected(item);
    }
}
