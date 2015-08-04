package com.sthelper.sthelper.business.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.AuthApi;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

public class ForgotPwdAction extends BaseAction {

    private View alertLayout1, alertLayout2, verifyLayout, passwordLayout, telLayout;

    private EditText telEt, codeEt, passwordEt;
    private Button getCodeBt, submitBt;

    int index = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd_action);
        initActionBar("找回密码");
        initView();
    }

    private void initView() {
        alertLayout1 = findViewById(R.id.verify_old_tel_alert);
        alertLayout2 = findViewById(R.id.input_new_tel_alert);

        verifyLayout = findViewById(R.id.verify_code_layout);
        passwordLayout = findViewById(R.id.newpassword_layout);
        telLayout = findViewById(R.id.tel_layout);

        telEt = (EditText) findViewById(R.id.bundle_tel);

        if (app.currentUserInfo != null) {
            telEt.setText(app.currentUserInfo.account);
        }

        passwordEt = (EditText) findViewById(R.id.newpassword);
        codeEt = (EditText) findViewById(R.id.code);

        getCodeBt = (Button) findViewById(R.id.send_verify_code);
        submitBt = (Button) findViewById(R.id.button);

        getCodeBt.setOnClickListener(onClickListener);
        submitBt.setOnClickListener(onClickListener);

        switchUI();
    }

    private void switchUI() {
        TextView num1 = (TextView) alertLayout1.findViewById(R.id.verify_old_tel_img);
        TextView text1 = (TextView) alertLayout1.findViewById(R.id.verify_old_tel_text);

        TextView num2 = (TextView) alertLayout2.findViewById(R.id.input_new_tel_img);
        TextView text2 = (TextView) alertLayout2.findViewById(R.id.input_new_tel_text);

        if (index == 0) {
            num1.setBackgroundResource(R.drawable.yellow_circle);
            text1.setTextColor(Color.parseColor("#fc7b00"));

            num1.setBackgroundResource(R.drawable.gray_circle);
            text1.setTextColor(Color.parseColor("#c0c0c0"));

            telLayout.setVisibility(View.VISIBLE);
            verifyLayout.setVisibility(View.GONE);
            passwordLayout.setVisibility(View.GONE);
            submitBt.setVisibility(View.GONE);
            submitBt.setText("确定");
        } else if (index == 1) {
            num1.setBackgroundResource(R.drawable.gray_circle);
            text1.setTextColor(Color.parseColor("#c0c0c0"));
            num1.setBackgroundResource(R.drawable.yellow_circle);
            text1.setTextColor(Color.parseColor("#fc7b00"));

            submitBt.setVisibility(View.VISIBLE);
            telLayout.setVisibility(View.GONE);
            verifyLayout.setVisibility(View.VISIBLE);
            passwordLayout.setVisibility(View.VISIBLE);

            submitBt.setText("确定");
        }

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == getCodeBt) {
                getVerifyCode();

            } else if (view == submitBt) {
                setNewPassword();
            }
        }
    };

    private void getVerifyCode() {
        processDialog.show();
        String tel = telEt.getText().toString();
        AuthApi api = new AuthApi();
        api.getVerifyCode(tel, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                int ret = response.optInt("ret");
                if (ret == 0) {
                    index = 1;
                    switchUI();
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

    private void setNewPassword() {
        processDialog.show();
        String code = codeEt.getText().toString();
        String newPassword = passwordEt.getText().toString();

        AuthApi authApi = new AuthApi();
        authApi.forgotPassword(code, newPassword, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (0 == response.optInt("ret")) {
                    SPUtil.clean();
                    ToastUtil.showToast(response.optString("error") + "");
                    finish();
                } else {
                    ToastUtil.showToast(response.optString("error")+"");
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
