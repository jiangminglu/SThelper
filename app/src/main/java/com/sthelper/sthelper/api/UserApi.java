package com.sthelper.sthelper.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by luffy on 15/7/4.
 */
public class UserApi extends BaseApi {

    /**
     * 获取用户信息
     *
     * @param uid
     * @param httpResponseHandler
     */
    public void getUserInfo(int uid, String token, JsonHttpResponseHandler httpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("token", token);
        get("User", "index", params, httpResponseHandler);
    }

    /**
     * 获取用户的订单列表
     *
     * @param uid
     * @param status                  0表示没付款,1付款了,2配送中,3完成
     * @param jsonHttpResponseHandler a=&&uid=1&status=0
     */
    public void getUserAddressList(int uid, int status, JsonHttpResponseHandler jsonHttpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("status", status);
        get("User", "getorder", params, jsonHttpResponseHandler);
    }

    /**
     * 获取石头列表
     *
     * @param cate_id                 1-大理石； 2-花岗岩
     * @param jsonHttpResponseHandler
     */
    public void getStoneList(int cate_id, JsonHttpResponseHandler jsonHttpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("cate_id", cate_id);
        get("Data", "stonelist", params, jsonHttpResponseHandler);
    }

    /**
     * 获取石头分类下面的列表
     *
     * @param stone_id
     * @param jsonHttpResponseHandler
     */
    public void getStoneChildList(int stone_id, JsonHttpResponseHandler jsonHttpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("stone_id", stone_id);
        get("Data", "getstone", params, jsonHttpResponseHandler);
    }

    /**
     * 发布石头
     *
     * @param params
     * @param jsonHttpResponseHandler
     */
    public void publishStone(RequestParams params, JsonHttpResponseHandler jsonHttpResponseHandler) {
        postStone(params, jsonHttpResponseHandler);
    }

}
