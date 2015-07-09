package com.sthelper.sthelper.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sthelper.sthelper.util.SPUtil;

/**
 * Created by luffy on 15/7/4.
 */
public class CommonApi extends BaseApi {

    /**
     * 获取地区列表
     *
     * @param httpResponseHandler
     */
    public void getAreaList(JsonHttpResponseHandler httpResponseHandler) {
        RequestParams params = new RequestParams();
        get("Data", "getarea", params, httpResponseHandler);
    }

    /**
     * 获取商圈列表
     *
     * @param httpResponseHandler
     */
    public void getBusinessList(JsonHttpResponseHandler httpResponseHandler) {
        RequestParams params = new RequestParams();
        int uid = SPUtil.getInt("uid");
        params.put("uid",uid);
        get("User", "areas", params, httpResponseHandler);
    }

}
