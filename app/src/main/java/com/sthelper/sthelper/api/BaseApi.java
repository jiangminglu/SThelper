package com.sthelper.sthelper.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
    }
    public static void get(String path,String method, RequestParams params, JsonHttpResponseHandler res) {
        initParam(params);
        params.put("a",method);
        params.put("m",path);
        client.get(MAIN_URL, params, res);
    }

//    params.put("g","api");
//    params.put("m","Passport");
//    params.put("sig","123456789987654321");
//    params.put("t","2013");
//    params.put("a","sendSMS");
//    params.put("mobile","13568882250");
    public static void post(String path,String method, RequestParams params, JsonHttpResponseHandler res) {
        initParam(params);
        params.put("m",path);
        params.put("a",method);
        client.post(MAIN_URL, params, res);
    }
    private static void  initParam(RequestParams params){
        params.put("g","api");
        params.put("sig","123456789987654321");
        params.put("t","2015");
    }
}
