package com.zzy.acommonlib;

import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.http.HProxy;
import com.zzy.commonlib.http.RequestCtx;
import com.zzy.commonlib.log.MyLog;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.encryptUtils.MD5Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.zzy.acommonlib.HttpConstants.*;
import static com.zzy.commonlib.http.HConstant.HTTP_METHOD_GET;
import static com.zzy.commonlib.http.HConstant.HTTP_METHOD_POST;

/**
 * @author zzy
 * @date 2018/7/31
 */

public class HttpUtils {

    private static final String TAG = "HttpUtils";
    private static final String contentType = "application/json";

    private HttpUtils() {
    }

    private static class LazyHolder {
        private static final HttpUtils IN = new HttpUtils();
    }

    public static HttpUtils getInstance() {
        return LazyHolder.IN;
    }

    public String getAppHeader() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("v", "1.0.0");
            jsonObject.put("appid", APP_ID);
            jsonObject.put("t", (int) (System.currentTimeMillis() / 1000));
            jsonObject.put("ver", "1.1");
            jsonObject.put("access-token", "");
            jsonObject.put("client-info", "hrr");
            jsonObject.put("client-version", AppUtils.getVersionName());
            jsonObject.put("appsecret", HttpConstants.APP_SECRET_KEY);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    //sign=md5(path+"|"+v+"|"+appid+"|"+appsecret+"|"+t+"|"+access-token+"|"+client-info+"|"+client-version+"|"+request body)
    //sign：/v1/content/get-banners?access-token=3fb0dca8db31c64c3c823e41ffef0a1b354f|1.0.0|android|123456|1552641169036||hrr|4.9.0.27|{"size":640,"apdid":50,"idfa":"ffffffff-c6a0-2392-0000-000000000000"}
    public Map<String, String> warpHeader(String actionName, String body) {
        Map<String, String> headerMap = new LinkedHashMap<>(9);
        headerMap.put("x-appid", APP_ID);
        headerMap.put("x-v", INTERFACE_V);
        headerMap.put("x-t", String.valueOf(((int) (System.currentTimeMillis() / 1000))));
        headerMap.put("x-ver", VER);
        headerMap.put("x-access-token", "");
        headerMap.put("x-client-info", "hrr");
        headerMap.put("x-client-version", AppUtils.getVersionName());
        headerMap.put("x-client-from", "2");

        String sign = actionName
                + "|" + headerMap.get("x-v")
                + "|" + headerMap.get("x-appid")
                + "|" + HttpConstants.APP_SECRET_KEY
                + "|" + headerMap.get("x-t")
                + "|" + headerMap.get("x-access-token")
                + "|" + headerMap.get("x-client-info")
                + "|" + headerMap.get("x-client-version")
                + "|" + body;

        MyLog.e(TAG, "x-sign：" + sign);
        headerMap.put("x-sign", MD5Utils.encodeByMD5(sign));
        return headerMap;
    }

    public JSONObject wrapBody(JSONObject rawBody) throws Exception {
        rawBody.put("pre_process", "");        // 检验加密串
        rawBody.put("geetest_challenge", ""); // 检验 geetest_challenge
        rawBody.put("geetest_validate", ""); // 检验 geetest_validate
        rawBody.put("geetest_seccode", ""); // 检验 geetest_seccode
        return rawBody;
    }

    /* request */
    public void req(String actionName, JSONObject body, final HInterface.DataCallback callback, final HInterface.JsonParser jsonParser) throws Exception {
        String reqBody = body.toString();
        HInterface.Decrypter decrypter = new HInterface.Decrypter() {
            @Override
            public String decrypt(String s) {
                MyLog.e(TAG, "data：" + s);
                return s;
            }
        };
//        String testUrl = "https://appport.hengyirong.com/2-0-0/index.php?r=Check/health";

        RequestCtx ctx = new RequestCtx.Builder(actionName)
                .method(HTTP_METHOD_POST)
                .headerMap(warpHeader(actionName, reqBody))
                .body(reqBody)
                .contentType(contentType)
                .callback(callback)
                .jsonParser(jsonParser)
                .decrypter(decrypter)
                .retryCount(3)
                .timerout(30)
                .build();

        MyLog.e(TAG, "请求服务 url:" + ctx.getUrl());
        MyLog.e(TAG, "请求服务 body:" + body.toString());

        HProxy.getInstance().request(ctx);

    }


}
