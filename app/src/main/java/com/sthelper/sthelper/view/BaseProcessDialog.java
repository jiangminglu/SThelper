package com.sthelper.sthelper.view;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by luffy on 15/6/27.
 */
public class BaseProcessDialog extends ProgressDialog {
    public BaseProcessDialog(Context context) {
        super(context);
    }

    public BaseProcessDialog(Context context, int theme) {
        super(context, theme);
    }
}
