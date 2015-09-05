package com.sthelper.sthelper.business;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sthelper.sthelper.FeedBackAction;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.api.CommonApi;
import com.sthelper.sthelper.business.auth.LoginAction;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.ToastUtil;
import com.sthelper.sthelper.util.Tools;

import org.apache.http.Header;
import org.json.JSONObject;

public class SettingAction extends BaseAction implements View.OnClickListener {

    RelativeLayout settingabout;
    RelativeLayout settingfeedbak;
    RelativeLayout settingcleancache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_action);
        initActionBar("设置");
        initView();
    }

    private void initView() {
        settingabout = (RelativeLayout) findViewById(R.id.setting_about);
        settingfeedbak = (RelativeLayout) findViewById(R.id.setting_feedbak);
        settingcleancache = (RelativeLayout) findViewById(R.id.setting_clean_cache);


        findViewById(R.id.setting_checkupdate).setOnClickListener(this);
        settingabout.setOnClickListener(this);
        settingfeedbak.setOnClickListener(this);
        settingcleancache.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        if (view == settingabout) {
            intent.setClass(mActivity, AboutAction.class);
            startActivity(intent);
        } else if (view == settingfeedbak) {
            intent.setClass(mActivity, FeedBackAction.class);
            startActivity(intent);
        } else if (view == settingcleancache) {
            processDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ImageLoader.getInstance().clearDiskCache();
                    ToastUtil.showToast("清理完毕");
                    processDialog.dismiss();
                }
            }, 2000);
        } else if (R.id.setting_checkupdate == view.getId()) {
            checkUpdate();
        }
    }

    private void checkUpdate() {
        processDialog.show();
        CommonApi api = new CommonApi();
        api.checkUpdate(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (response.optInt("ret") == 0) {
                    JSONObject result = response.optJSONObject("result");
                    int version_code = result.optInt("version_code");
                    final String url = result.optString("apk_url");
                    String content = result.optString("upgrade_point");
                    int code = Tools.getVersionCode(mActivity);
                    if (version_code > code) {
                        new AlertDialog.Builder(mActivity).setMessage(content).setPositiveButton("更新", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Uri uri = Uri.parse(url);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
                    } else {
                        ToastUtil.showToast("已经是最新版本");
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
            }
        });
    }

}
