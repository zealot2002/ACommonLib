package com.zzy.commonlib.log;
import android.content.Context;
import android.util.Log;

/**
 * @author zzy
 * @date 2018/8/14
 */

public class MyLog {
    private static boolean enable = true;
    public static void e(String tag, String s){
        if(enable)
            Log.e(tag,s);
    }

    public static void w(String tag, String s){
        if(enable)
            Log.w(tag,s);
    }
    public static void d(String tag, String s){
        if(enable)
            Log.d(tag,s);
    }
    public static void e(String s){
        if(!enable) return;

        String tag = generateTag();
        Log.e(tag, s);
    }
    public static void w(String s){
        if(!enable) return;

        String tag = generateTag();
        Log.w(tag, s);
    }
    public static void d(String s){
        if(!enable) return;

        String tag = generateTag();
        Log.d(tag, s);
    }

    public static void init(boolean bOpen, Context context,String logName) {
        enable = bOpen;
        if(enable){
            LogcatHelper.getInstance().init(context,logName);
            LogcatHelper.getInstance().start();
        }
    }

    public static void exit(){
        LogcatHelper.getInstance().stop();
    }
    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return tag;
    }

}
