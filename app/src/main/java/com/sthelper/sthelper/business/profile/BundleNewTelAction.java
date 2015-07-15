package com.sthelper.sthelper.business.profile;

import android.app.Activity;
import android.graphics.Color;
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
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

public class BundleNewTelAction extends BaseAction {

    private View alertLayout1, alertLayout2;

    private EditText telEt, codeEt;
    private Button getCodeBt, submitBt;

    int index = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle_new_tel_action);
        initActionBar("绑定新手机");
        init();
    }

    private void init() {

        alertLayout1 = findViewById(R.id.verify_old_tel_alert);
        alertLayout2 = findViewById(R.id.input_new_tel_alert);


        telEt = (EditText) findViewById(R.id.bundle_tel);

        if (app.currentUserInfo != null) {
            telEt.setText(app.currentUserInfo.account);
        }

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

            submitBt.setText("验证");
        } else if (index == 1) {
            num1.setBackgroundResource(R.drawable.gray_circle);
            text1.setTextColor(Color.parseColor("#c0c0c0"));
            num1.setBackgroundResource(R.drawable.yellow_circle);
            text1.setTextColor(Color.parseColor("#fc7b00"));

            submitBt.setText("绑定");
        }

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == getCodeBt) {
                getVerifyCode();
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
                String error = response.optString("error");
                ToastUtil.showToast(error);
                if (ret == 0) {
                    index = 1;
                    switchUI();
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
