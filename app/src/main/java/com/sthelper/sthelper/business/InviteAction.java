package com.sthelper.sthelper.business;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.util.ToastUtil;
import com.sthelper.sthelper.util.Util;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class InviteAction extends BaseAction {

    private IWXAPI api;
    LinearLayout sharewxfriend;
    LinearLayout sharemessage;
    LinearLayout shareqq;
    LinearLayout sharewxpost;
    LinearLayout shareweibo;
    private String title = "水头助手";
    private String desc = "快来使用水头助手";
    private String url = "http://120.26.49.208/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_action);
        initActionBar("邀请好友");
        api = WXAPIFactory.createWXAPI(this, "wx3cace427704022f0");
        initView();
    }

    private void qqShare() {
        Intent weiboIntent = new Intent(Intent.ACTION_SEND);
        weiboIntent.setType("text/plain");
        PackageManager pm = getPackageManager();
        List<ResolveInfo> matches = pm.queryIntentActivities(weiboIntent,
                PackageManager.MATCH_DEFAULT_ONLY);
        String packageName = "com.tencent.mobileqq";
        ResolveInfo info = null;
        for (ResolveInfo each : matches) {
            String pkgName = each.activityInfo.applicationInfo.packageName;
            if (packageName.equals(pkgName)) {
                info = each;
                break;
            }
        }
        if (info != null) {
            weiboIntent.setClassName(packageName, info.activityInfo.name);
            weiboIntent.putExtra(Intent.EXTRA_TEXT, desc + url);
            weiboIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(app.appLogo)));
            startActivity(weiboIntent);
        } else {
            ToastUtil.showToast("你没有安装QQ客户端");
        }

    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {

        }

        @Override
        public void onComplete(Object response) {
            ToastUtil.showToast("分享成功");
        }

        @Override
        public void onError(UiError e) {
        }
    };

    private void initView() {
        sharewxfriend = (LinearLayout) findViewById(R.id.share_wx_friend);
        sharemessage = (LinearLayout) findViewById(R.id.share_message);
        shareqq = (LinearLayout) findViewById(R.id.share_qq);
        sharewxpost = (LinearLayout) findViewById(R.id.share_wx_post);
        shareweibo = (LinearLayout) findViewById(R.id.share_weibo);

        sharewxfriend.setOnClickListener(onClickListener);
        sharemessage.setOnClickListener(onClickListener);
        shareqq.setOnClickListener(onClickListener);
        sharewxpost.setOnClickListener(onClickListener);
        shareweibo.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == sharewxfriend) {
                send2wx(true);
            } else if (view == sharewxpost) {
                send2wx(false);
            } else if (view == shareweibo) {
                sinaShare();
            } else if (view == shareqq) {
                qqShare();
            }
        }
    };

    private void sinaShare() {
        Intent weiboIntent = new Intent(Intent.ACTION_SEND);
        weiboIntent.setType("image/*");
        PackageManager pm = getPackageManager();
        List<ResolveInfo> matches = pm.queryIntentActivities(weiboIntent,
                PackageManager.MATCH_DEFAULT_ONLY);
        String packageName = "com.sina.weibo";
        ResolveInfo info = null;
        for (ResolveInfo each : matches) {
            String pkgName = each.activityInfo.applicationInfo.packageName;
            if (packageName.equals(pkgName)) {
                info = each;
                break;
            }
        }
        if (info != null) {
            weiboIntent.setClassName(packageName, info.activityInfo.name);
            weiboIntent.putExtra(Intent.EXTRA_TEXT, desc + url);
            weiboIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(app.appLogo)));
            startActivity(weiboIntent);
        } else {
            ToastUtil.showToast("你没有安装微博客户端");
        }

    }

    private void qq() {
        Intent weiboIntent = new Intent(Intent.ACTION_SEND);
        weiboIntent.setType("image/*");
        PackageManager pm = getPackageManager();
        List<ResolveInfo> matches = pm.queryIntentActivities(weiboIntent,
                PackageManager.MATCH_DEFAULT_ONLY);
        String packageName = "com.sina.weibo";
        ResolveInfo info = null;
        for (ResolveInfo each : matches) {
            String pkgName = each.activityInfo.applicationInfo.packageName;
            if (packageName.equals(pkgName)) {
                info = each;
                break;
            }
        }
        if (info != null) {
            weiboIntent.setClassName(packageName, info.activityInfo.name);
            weiboIntent.putExtra(Intent.EXTRA_TEXT, desc + url);
            weiboIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(app.appLogo)));
            startActivity(weiboIntent);
        } else {
            ToastUtil.showToast("你没有安装微博客户端");
        }

    }

    private void send2wx(boolean flag) {
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.app_logo);
        thumb = Bitmap.createScaledBitmap(thumb, 50, 50, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.baidu.com";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "水头助手";
        msg.description = "";
        msg.thumbData = Util.bmpToByteArray(thumb, true);
        req.transaction = "webpage";
        req.message = msg;
        req.scene = flag ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }
}
