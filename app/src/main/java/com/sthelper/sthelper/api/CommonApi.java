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
        params.put("uid", uid);
        get("User", "areas", params, httpResponseHandler);
    }

    /**
     * 用户反馈
     *
     * @param content
     * @param httpResponseHandler
     */
    public void feedback(String content, JsonHttpResponseHandler httpResponseHandler) {
        RequestParams params = new RequestParams();
        int uid = SPUtil.getInt("uid");
        params.put("uid", uid);
        params.put("content", content);
        params.put("app_id", "1");
        get("User", "appadvice", params, httpResponseHandler);
    }

    /**
     * 检查版本更新
     *
     * @param httpResponseHandler
     */
    public void checkUpdate(JsonHttpResponseHandler httpResponseHandler) {
        RequestParams params = new RequestParams();
        get("Data", "versionupdate", params, httpResponseHandler);
    }

    /**
     * 搜索店铺
     *
     * @param shop_name
     * @param handler
     */
    public void searchShop(String shop_name, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("shop_name", shop_name);
        get("Data", "searchshop", params, handler);
    }

    /**
     * 搜索商品
     *
     * @param goods_name
     * @param handler
     */
    public void searchGoods(String goods_name, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("goods_name", goods_name);
        get("Data", "searchgoods", params, handler);
    }

    /**
     * 搜索石头
     *
     * @param stone_name
     * @param handler
     */
    public void searchStone(String stone_name, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("stone_name", stone_name);
        get("Data", "searchstone", params, handler);
    }

}
