package com.sthelper.sthelper.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.util.SPUtil;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by luffy on 15/6/30.
 */
public class BaseApi {
    private static final String MAIN_URL = "http://120.26.49.208/index.php";
    private static AsyncHttpClient client = new AsyncHttpClient();    //实例话对象

    public static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        client.setTimeout(11000);   //设置链接超时，如果不设置，默认为10s
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static void get(String path, String method, RequestParams params, JsonHttpResponseHandler res) {
        initParam(params);
        params.put("m", path);
        params.put("a", method);
        client.get(MAIN_URL, params, res);
    }

    public static void post(String path, String method, RequestParams params, JsonHttpResponseHandler res) {
        initParam(params);
        params.put("m", path);
        params.put("a", method);
        client.post(MAIN_URL, params, res);
    }

    private static void initParam(RequestParams params) {

        String token = SPUtil.getString("token");
        if (token != null && !"".equals(token)) {
            params.put("token", token);
        }
        params.put("g", "api");
        params.put("sig", "123456789987654321");
        params.put("t", "2015");
    }
    public static void postStone(RequestParams params, JsonHttpResponseHandler res) {
        String token = SPUtil.getString("token");
        if (token != null && !"".equals(token)) {
            params.put("token", token);
        }
        client.post(MAIN_URL + "?m=User&a=postmsg&g=api&t=2015&sig=123456789987654321", params, res);
    }
    public static void postAvatar(RequestParams params, JsonHttpResponseHandler res) {
        String token = SPUtil.getString("token");
        if (token != null && !"".equals(token)) {
            params.put("token", token);
        }
        client.post(MAIN_URL+"?m=User&a=useredit&g=api&t=2015&sig=123456789987654321", params, res);
    }
}
