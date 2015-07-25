package com.sthelper.sthelper.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.util.SPUtil;

/**
 * Created by luffy on 15/7/7.
 */
public class ShopingApi extends BaseApi {
    /**
     * 添加地址
     *
     * @param httpResponseHandler
     */
    public void addAddressItem(String username, String mobile, String address, JsonHttpResponseHandler httpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("name", username);
        params.put("mobile", mobile);
        params.put("addr", address);
        int uid = SPUtil.getInt("uid");
        params.put("uid", uid);


        params.put("area_id", SApp.getInstance().business.area_id);
        params.put("business_id", SApp.getInstance().business.business_id);
        get("User", "addressadd", params, httpResponseHandler);
    }

    /**
     * 编辑收货地址
     *
     * @param username
     * @param mobile
     * @param address
     * @param httpResponseHandler
     */
    public void editAddressItem(int id, String username, String mobile, String address, JsonHttpResponseHandler httpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("name", username);
        params.put("mobile", mobile);
        params.put("addr", address);
        int uid = SPUtil.getInt("uid");
        params.put("uid", uid);
        params.put("addr_id", id);


        params.put("area_id", SApp.getInstance().business.area_id);
        params.put("business_id", SApp.getInstance().business.business_id);
        get("User", "editaddr", params, httpResponseHandler);
    }

    /**
     * 获取用户订单列表
     *
     * @param httpResponseHandler
     */
    public void getAddressList(JsonHttpResponseHandler httpResponseHandler) {
        RequestParams params = new RequestParams();
        int uid = SPUtil.getInt("uid");
        params.put("uid", uid);

        get("User", "useraddr", params, httpResponseHandler);
    }

    /**
     * 删除收货地址
     *
     * @param addr_id
     * @param httpResponseHandler
     */
    public void delelteAddress(int addr_id, JsonHttpResponseHandler httpResponseHandler) {
        RequestParams params = new RequestParams();
        int uid = SPUtil.getInt("uid");
        params.put("uid", uid);
        params.put("addr_id", addr_id);

        get("User", "deleteaddr", params, httpResponseHandler);
    }

    /**
     * 提交订单
     *
     * @param params
     * @param handler
     */
    public void submitOrder(RequestParams params, JsonHttpResponseHandler handler) {
        get("User", "setorder", params, handler);
    }
}
