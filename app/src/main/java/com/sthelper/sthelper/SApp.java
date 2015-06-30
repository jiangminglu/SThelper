package com.sthelper.sthelper;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by luffy on 15/6/13.
 */
public class SApp extends Application {

    public int screenH = 0;
    public int screenW = 0;
    public float density = 0f;

    private static SApp app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initScreenWH();
    }
    public static SApp getInstance(){
        return  app;
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
}
