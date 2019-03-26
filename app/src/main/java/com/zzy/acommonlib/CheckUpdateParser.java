package com.zzy.acommonlib;

import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;

import org.json.JSONException;

/**
 * zzy
 */
public class CheckUpdateParser implements HInterface.JsonParser {
    private static final String TAG = "CheckUpdateParser";
    @Override
    public Object[] parse(String s) throws JSONException {
//        if (s == null) {
//            throw new JSONException("server return null");
//        }
//        JSONTokener jsonParser = new JSONTokener(s);
//        JSONObject obj = (JSONObject) jsonParser.nextValue();
//        String errorCode = obj.getString(HttpConstants.ERROR_CODE);
//        if (errorCode.equals(HttpConstants.NO_ERROR)) {
//            JSONObject object = obj.getJSONObject("data");
//
//            return new Object[]{HConstant.SUCCESS, ""};
//        } else {
//            Log.e(TAG,"errorCode :"+errorCode );
//            ToastUtils.showShort("errorCode:"+errorCode);
//            String msg = obj.getString(HttpConstants.ERROR_MESSAGE);
//            return new Object[]{HConstant.FAIL, msg};
//        }
        return new Object[]{HConstant.SUCCESS, ""};
    }
}
