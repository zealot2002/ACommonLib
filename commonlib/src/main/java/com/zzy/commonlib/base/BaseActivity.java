package com.zzy.commonlib.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.zhy.autolayout.AutoLayoutActivity;
import com.zzy.commonlib.utils.ActivityManager;


public abstract class BaseActivity extends AutoLayoutActivity {

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().finishActivity(this);
    }
}
