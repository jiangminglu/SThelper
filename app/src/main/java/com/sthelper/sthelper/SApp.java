package com.sthelper.sthelper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Environment;
import android.view.Display;
import android.view.WindowManager;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sthelper.sthelper.bean.Business;
import com.sthelper.sthelper.bean.GoodsInfo;
import com.sthelper.sthelper.bean.UserInfo;

import java.io.File;
import java.util.HashMap;

/**
 * Created by luffy on 15/6/13.
 */
public class SApp extends Application {

    public int screenH = 0;
    public int screenW = 0;
    public float density = 0f;
    public UserInfo currentUserInfo;
    public Business business;
    private static SApp app;
    public SharedPreferences preferences;
    public String rootPath, cachePath, imagePath,appLogo;
    public static final String IMG_URL = "http://120.26.49.208/attachs/";

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        rootPath = Environment.getExternalStorageDirectory() + "/sthelper";
        cachePath = rootPath + "/cache";
        imagePath = rootPath + "/img";
        appLogo = app.imagePath + "/" + "app_logo.jpg";
        initDir();
        initScreenWH();
        initImageLoader();
        preferences = getApplicationContext().getSharedPreferences("sthelper", Activity.MODE_PRIVATE);
    }

    private void initDir() {
        File file = new File(rootPath);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(cachePath);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(imagePath);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static SApp getInstance() {
        return app;
    }

    private void initScreenWH() {
        WindowManager windowManager = (WindowManager) this.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point localPoint = new Point();
        display.getSize(localPoint);
        this.screenH = localPoint.y;
        this.screenW = localPoint.x;
        this.density = getResources().getDisplayMetrics().density;
    }

    public void initImageLoader() {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(getApplicationContext());
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(100 * 1024 * 1024); // 50 MiB
        config.memoryCacheSize(30 * 1014 * 1024);/*手机内存设置*/
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        ImageLoader.getInstance().init(config.build());
    }
}
