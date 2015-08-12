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

    /**
     * 用户反馈
     * @param content
     * @param httpResponseHandler
     */
    public void feedback(String content,JsonHttpResponseHandler httpResponseHandler) {
        RequestParams params = new RequestParams();
        int uid = SPUtil.getInt("uid");
        params.put("uid",uid);
        params.put("content",content);
        params.put("app_id","1");
        get("User", "appadvice", params, httpResponseHandler);
    }

    /**
     * 检查版本更新
     * @param httpResponseHandler
     */
    public void checkUpdate(JsonHttpResponseHandler httpResponseHandler) {
        RequestParams params = new RequestParams();
        get("Data", "versionupdate", params, httpResponseHandler);
    }

}
