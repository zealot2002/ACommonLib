package com.zzy.commonlib.utils;

import android.app.*;
import android.content.Context;

/**
 * @author zzy
 * @date 2018/8/14
 */

public class ProcessUtils {
    public static String getCurrentProcessName(Application application) {
        int pid = android.os.Process.myPid();
        String processName = "";
        android.app.ActivityManager manager = (android.app.ActivityManager) application.getSystemService
                (Context.ACTIVITY_SERVICE);
        for (android.app.ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
            }
        }
        return processName;
    }
    public static boolean isMainProcess(Application application) {
        String packageName = application.getPackageName();
        return packageName.equals(getCurrentProcessName(application));
    }
}
