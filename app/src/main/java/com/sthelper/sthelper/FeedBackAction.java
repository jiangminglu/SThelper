package com.sthelper.sthelper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.api.CommonApi;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;


public class FeedBackAction extends BaseAction implements View.OnClickListener {

    TextView feedbacktitle;
    TextView feedbackhint;
    Button feedbacksend;
    EditText feedbackcontentet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_action);
        initActionBar("意见反馈");
        initView();
    }


    private void initView() {
        feedbacktitle = (TextView) findViewById(R.id.feedback_title);
        feedbackhint = (TextView) findViewById(R.id.feedback_hint);
        feedbacksend = (Button) findViewById(R.id.feedback_send);
        feedbackcontentet = (EditText) findViewById(R.id.feedback_content_et);


        feedbacksend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        processDialog.show();
        CommonApi api = new CommonApi();
        api.feedback(feedbackcontentet.getText() + "", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (0 == response.optInt("ret")) {
                    ToastUtil.showToast("反馈成功，谢谢你的支持");
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
