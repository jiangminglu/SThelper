package com.sthelper.sthelper.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by luffy on 15/7/4.
 */
public class CommonApi extends BaseApi {

    /**
     * 获取地区列表
     * @param httpResponseHandler
     */
    public void getAreaList(JsonHttpResponseHandler httpResponseHandler){
        RequestParams params = new RequestParams();
        get("Data","getarea",params,httpResponseHandler);
    }

}
