package com.zzy.commonlib;

import android.app.Application;

import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;

/**
 * @author zzy
 * @date 2019/2/14
 */

public class CommonLibHelper {
    public static void init(Application application){
        AppUtils.init(application);
        ToastUtils.init(application);
    }
}
