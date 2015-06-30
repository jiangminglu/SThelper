package com.sthelper.sthelper.business.auth;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.AuthApi;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.util.ToastUtil;
import com.sthelper.sthelper.view.BaseProcessDialog;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterAction extends BaseAction {


    private View alertLayout1;
    private View alertLayout2;
    private View alertLayout3;
    private int index = 0;
    private View layout1;
    private View layout2;
    private View layout3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_action);
        initActionBar("注册");
        init();
        updateLayout();
    }

    private void init() {
        this.layout1 = findViewById(R.id.register_input_tel_layout);
        this.layout2 = findViewById(R.id.register_input_code_layout);
        this.layout3 = findViewById(R.id.register_input_password_layout);

        this.alertLayout1 = findViewById(R.id.register_input_tel_alert);
        this.alertLayout2 = findViewById(R.id.register_input_code_alert);
        this.alertLayout3 = findViewById(R.id.register_input_password_alert);
    }

    private void updateLayout() {
        if (this.index == 0) {
            this.layout1.setVisibility(View.VISIBLE);
            this.layout2.setVisibility(View.GONE);
            this.layout3.setVisibility(View.GONE);

            this.alertLayout1.findViewById(R.id.register_input_tel_alert_index).setBackgroundResource(R.drawable.yellow_circle);
            ((TextView) this.alertLayout1.findViewById(R.id.register_input_tel_alert_content)).setTextColor(Color.parseColor("#fc7b00"));

            this.alertLayout2.findViewById(R.id.register_input_code_alert_index).setBackgroundResource(R.drawable.gray_circle);
            ((TextView) this.alertLayout2.findViewById(R.id.register_input_code_alert_content)).setTextColor(Color.parseColor("#c0c0c0"));

            this.alertLayout3.findViewById(R.id.register_input_password_alert_index).setBackgroundResource(R.drawable.gray_circle);
            ((TextView) this.alertLayout3.findViewById(R.id.register_input_password_alert_content)).setTextColor(Color.parseColor("#c0c0c0"));
            this.layout1.findViewById(R.id.register_input_tel_btn).setOnClickListener(new View.OnClickListener() {
                public void onClick(View paramAnonymousView) {
                }
            });
        } else if (this.index == 1) {
            this.layout1.setVisibility(View.GONE);
            this.layout2.setVisibility(View.VISIBLE);
            this.layout3.setVisibility(View.GONE);

            this.alertLayout1.findViewById(R.id.register_input_tel_alert_index).setBackgroundResource(R.drawable.yellow_circle);
            ((TextView) this.alertLayout1.findViewById(R.id.register_input_tel_alert_content)).setTextColor(Color.parseColor("#fc7b00"));

            this.alertLayout2.findViewById(R.id.register_input_code_alert_index).setBackgroundResource(R.drawable.yellow_circle);
            ((TextView) this.alertLayout2.findViewById(R.id.register_input_code_alert_content)).setTextColor(Color.parseColor("#fc7b00"));

            this.alertLayout3.findViewById(R.id.register_input_password_alert_index).setBackgroundResource(R.drawable.gray_circle);
            ((TextView) this.alertLayout3.findViewById(R.id.register_input_password_alert_content)).setTextColor(Color.parseColor("#c0c0c0"));
        } else if (this.index == 2) {

            this.layout1.setVisibility(View.GONE);
            this.layout2.setVisibility(View.GONE);
            this.layout3.setVisibility(View.VISIBLE);

            this.alertLayout1.findViewById(R.id.register_input_tel_alert_index).setBackgroundResource(R.drawable.yellow_circle);
            ((TextView) this.alertLayout1.findViewById(R.id.register_input_tel_alert_content)).setTextColor(Color.parseColor("#fc7b00"));

            this.alertLayout2.findViewById(R.id.register_input_code_alert_index).setBackgroundResource(R.drawable.yellow_circle);
            ((TextView) this.alertLayout2.findViewById(R.id.register_input_code_alert_content)).setTextColor(Color.parseColor("#fc7b00"));

            this.alertLayout3.findViewById(R.id.register_input_password_alert_index).setBackgroundResource(R.drawable.yellow_circle);
            ((TextView) this.alertLayout3.findViewById(R.id.register_input_password_alert_content)).setTextColor(Color.parseColor("#fc7b00"));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register_action, menu);
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
            if (index == 0) {
                index++;
                updateLayout();
                EditText telEt = (EditText) findViewById(R.id.register_username_et);
                getSMSCode(telEt.getText().toString());
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getSMSCode(String mobile) {
        processDialog.show();
        AuthApi api = new AuthApi();
        api.getVerifyCode(mobile, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                ToastUtil.showToast("验证码已发送...");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
            }
        });
    }
}
