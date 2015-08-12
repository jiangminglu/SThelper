package com.sthelper.sthelper.business;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.util.ToastUtil;
import com.tencent.connect.share.QQShare;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;
import java.util.List;

public class InviteAction extends BaseAction {


    LinearLayout sharewxfriend;
    LinearLayout sharemessage;
    LinearLayout shareqq;
    LinearLayout sharewxpost;
    LinearLayout shareweibo;
    private String title = "水头助手";
    private String desc = "快来使用水头助手";
    private String url = "http://www.baidu.com";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_action);
        initActionBar("邀请好友");
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

    /**
     * 分享信息到朋友
     *
     * @param file,假如图片的路径为path，那么file = new File(path);
     */
    private void shareToFriend(File file) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm",
                "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, "我是文字");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        startActivity(intent);
    }

    /**
     * 分享信息到朋友圈
     *
     * @param file，假如图片的路径为path，那么file = new File(path);
     */
    private void shareToTimeLine(File file) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(componentName);

        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.putExtra(Intent.EXTRA_TEXT, "固定字段");
        intent.setType("image/*");

        startActivity(intent);
    }

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
                shareToFriend(new File(app.appLogo));
            } else if (view == sharewxpost) {
                shareToTimeLine(new File(app.appLogo));
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
}
