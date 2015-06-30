package com.sthelper.sthelper.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by luffy on 15/6/30.
 */
public class AuthApi extends BaseApi {

    public void getVerifyCode(String phone,JsonHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put("mobile",phone);
        post("Passport", "sendSMS", params, responseHandler);
    }
}
