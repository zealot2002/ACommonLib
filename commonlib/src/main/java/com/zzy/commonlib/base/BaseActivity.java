package com.zzy.commonlib.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.zhy.autolayout.AutoLayoutActivity;

public abstract class BaseActivity extends AutoLayoutActivity {

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
