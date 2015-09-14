package com.sthelper.sthelper.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by luffy on 15/6/30.
 */
public class AuthApi extends BaseApi {

    /**
     * 获取验证码
     *
     * @param phone
     * @param responseHandler
     */
    public void getVerifyCode(String phone, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("mobile", phone);
        get("Passport", "sendSMS", params, responseHandler);
    }

    /**
     * 填写昵称，密码，完成注册
     *
     * @param params
     * @param responseHandler
     */
    public void complateRegister(RequestParams params, JsonHttpResponseHandler responseHandler) {
        get("Passport", "register", params, responseHandler);
    }

    /**
     * 使用手机号，密码登陆
     *
     * @param account
     * @param password
     * @param responseHandler
     */
    public void loginByPassword(String account, String password, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("account", account);
        params.put("password", password);
        get("Passport", "login", params, responseHandler);
    }

    /**
     * 忘记密码
     *
     * @param code
     * @param newpwd
     * @param responseHandler
     */
    public void forgotPassword(String code, String newpwd, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("code", code);
        params.put("newpwd", newpwd);
        get("Passport", "forgetpw", params, responseHandler);
    }

    /**
     * 修改密码
     *
     * @param oldpwd
     * @param newpwd
     * @param responseHandler
     */
    public void changePassword(int uid,String oldpwd, String newpwd, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("oldpwd", oldpwd);
        params.put("newpwd", newpwd);
        params.put("uid",uid);
        changePwd(params, responseHandler);
    }

}
