package com.sthelper.sthelper.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by luffy on 15/7/22.
 */
public class FoodApi extends BaseApi {

    /**
     * 获取食品店铺列表
     *
     * @param page
     * @param cateId  1食， 71饮
     * @param handler
     */
    public void getFoodStoreList(int page, int cateId, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("cate_id", cateId);
        get("Data", "getshops", params, handler);
    }

}
