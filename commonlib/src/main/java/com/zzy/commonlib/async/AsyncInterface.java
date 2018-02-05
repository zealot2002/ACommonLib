package com.zzy.commonlib.async;

/**
 * Created by dell-7020 on 2017/11/20.
 */

public interface AsyncInterface {
    interface Callback{
        void onCallback(boolean bResult, Object data, Object tagData);
    }
}
