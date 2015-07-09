package com.sthelper.sthelper.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by luffy on 15/7/7.
 */
public class ShopingApi extends BaseApi {
    /**
     * 添加地址
     * @param httpResponseHandler
     */
    public void addAddressItem(JsonHttpResponseHandler httpResponseHandler){
        RequestParams params = new RequestParams();
        get("User","addressadd",params,httpResponseHandler);
    }
}
