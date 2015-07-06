package com.sthelper.sthelper.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by luffy on 15/7/4.
 */
public class UserApi extends BaseApi {

    /**
     * 获取用户信息
     * @param uid
     * @param httpResponseHandler
     */
    public void getUserInfo(int uid,String token,JsonHttpResponseHandler httpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("token",token);
        get("User", "index", params, httpResponseHandler);
    }
    /**
     * 获取用户收获地址列表
     * @param uid
     * @param token
     * @param httpResponseHandler
     */
    public void getUserAddressList(int uid,String token,JsonHttpResponseHandler httpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("token",token);
        get("User", "useraddr", params, httpResponseHandler);
    }
}
