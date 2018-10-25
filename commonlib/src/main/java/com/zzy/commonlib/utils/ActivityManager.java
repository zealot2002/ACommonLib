package com.zzy.commonlib.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Stack;

import static android.content.Context.ACTIVITY_SERVICE;

public class ActivityManager {

    private static Stack<Activity> activityStack;

    public static ActivityManager getInstance() {
        return ViewManagerHolder.sInstance;
    }

    private static class ViewManagerHolder {
        private static final ActivityManager sInstance = new ActivityManager();
    }

    private ActivityManager() {
    }
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }
    }
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
    public void exitApp(Context context) {
        Log.e("ActivityManager", "app exit" );
        try {
            finishAllActivity();
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            Log.e("ActivityManager", "app exit" + e.getMessage());
        }
    }
}
