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

    public void getFoodStoreList(int page, String type, String taste, int cateId, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("cate_id", cateId);
        if (type != null)
            params.put("type", type);
        if (taste != null) {
            params.put("taset", taste);
        }
        get("Data", "getshops", params, handler);
    }

    /**
     * 获取店铺详情
     *
     * @param shop_id 店铺id
     * @param cateId  分类id ， 1食，71，饮
     * @param handler
     */
    public void getStoreDetail(int shop_id, int cateId, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("shop_id", shop_id);
        params.put("cat", cateId);
        params.put("order", "s");
        get("Data", "shop", params, handler);
    }

    /**
     * 获取店铺商品列表
     *
     * @param shop_id 店铺id
     * @param handler
     */
    public void getShopGoodsList(int shop_id, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("shop_id", shop_id);
        get("Data", "getshopgoods", params, handler);
    }

}
