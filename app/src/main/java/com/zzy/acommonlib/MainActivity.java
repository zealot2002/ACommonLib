package com.zzy.acommonlib;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zzy.commonlib.base.BaseActivity;
import com.zzy.commonlib.utils.ActivityManager;
import com.zzy.commonlib.utils.ApplicationUtils;
import com.zzy.commonlib.utils.FileUtils;

public class MainActivity extends Activity{
    private static final String TAG = "MainActivity";

    private Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();

        try{
//            String currentActivity = ActivityManager.getInstance().currentActivity().toString();
//            Log.e(TAG,"currentActivity: "+currentActivity);
//            Toast.makeText(ApplicationUtils.getApp(), "currentActivity:" , Toast.LENGTH_SHORT).show();
            TestHttp.doTestGet();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
