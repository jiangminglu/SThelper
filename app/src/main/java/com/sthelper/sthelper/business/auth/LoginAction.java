package com.sthelper.sthelper.business.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.AuthApi;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

public class LoginAction extends BaseAction {

    private Button loginBt;
    private EditText passwordEt;
    private TextView registerTv;
    private EditText usernameEt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_action);
        initActionBar("登陆");
        init();
    }

    private void init() {
        this.usernameEt = ((EditText) findViewById(R.id.login_username_et));
        this.passwordEt = ((EditText) findViewById(R.id.login_password_et));
        this.registerTv = ((TextView) findViewById(R.id.goto_register));
        this.registerTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                Intent localIntent = new Intent();
                localIntent.setClass(LoginAction.this, RegisterAction.class);
                LoginAction.this.startActivity(localIntent);
            }
        });
        this.loginBt = ((Button) findViewById(R.id.login_btn));
        this.loginBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                login();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void login() {
        String account = usernameEt.getText().toString();
        String password = passwordEt.getText().toString();

        processDialog.show();
        AuthApi authApi = new AuthApi();
        authApi.loginByPassword(account, password, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                int ret = response.optInt("ret");
                if (ret != 0) {
                    String error = response.optString("error");
                    ToastUtil.showToast(error);
                } else {
                    JSONObject result = response.optJSONObject("result");
                    int uid = result.optInt("uid");
                    String token = result.optString("token");
                    SPUtil.save("uid", uid);
                    SPUtil.save("token", token);
                    ToastUtil.showToast("登陆成功");
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
            }
        });

    }
}
