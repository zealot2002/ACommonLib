package com.zzy.acommonlib;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;

/**
 * @author zzy
 * @date 2019/3/2
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        ToastUtils.init(this);
        AppUtils.addOnAppStatusChangedListener(new AppUtils.OnAppStatusChangedListener() {
            @Override
            public void onForeground() {
                ToastUtils.showShort("got!!!!!!!!!!");
            }

            @Override
            public void onBackground() {

            }
        });
    }
}
