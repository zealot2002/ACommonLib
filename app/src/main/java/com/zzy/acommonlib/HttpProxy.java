package com.zzy.acommonlib;

import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.utils.AppUtils;

import org.json.JSONObject;

/**
 * @author zzy
 * @date 2019/3/25
 */

public class HttpProxy {
    public static void testPing(String url,HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();

        HttpUtils.getInstance().req(
                url,
                reqBody,
                callback,
                new CheckUpdateParser());
    }
    public static void checkVersion(HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("type", 1);
        reqBody.put("version", AppUtils.getVersionCode());
        reqBody.put("versioncode", AppUtils.getVersionName());

        HttpUtils.getInstance().req(
                HttpConstants.APP_UPDATE,
                reqBody,
                callback,
                new CheckUpdateParser());
    }

//    public static void checkUpdate(HInterface.DataCallback callback) {
//        try {
//            JSONObject reqBody = new JSONObject();
//            reqBody.put("type", 1);
//            reqBody.put("version", "");
//            reqBody.put("versioncode","4.6.0");
//            reqBody.put("Num", 10031);
//            reqBody.put("Control", "CheckUpdates");
//
//            setCommonParams(reqBody);
//            String httpUrl = "https://appport.hengyirong.com/check/index.php?r=user/CheckUpdates";
////            postWithConnectTime(activity.hashCode(), httpUrl, getUpdateParams(params), callback, 10000);
//
//            HttpUtils.getInstance().req(
//                    HttpConstants.OLD_CHECK_UPDATE,
//                    reqBody,
//                    callback,
//                    new CheckUpdateParser());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private static String getToken() {
//        return new StringBuilder("hengyirong-")
//                .append(DateUtil.getUnixTimeGMT())
//                .append("-")
//                .append(new Random().nextInt(100))
//                .toString();
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public static Map<String, String> getUpdateParams(JSONObject jsonObject) {
//        Map<String, String> params = new ArrayMap<>(4);
//        params.put("Signature", DESCryptUtil.encrypt(jsonObject.toString(), "b79ff42fc9d045aed383243a"));
//        params.put("Token", DESCryptUtil.encrypt(getToken(), GlobalConstants.TOKEN_UPDATE));
//        params.put("WebFrom", "2");
//        // 访问用户数据的请求秘钥
//        if (jsonObject.has("uid") || jsonObject.has("user_id")) {
//            String key = AccountModel.getAccountModel().getAccount().getYzm();
//            if (key != null) {
//                params.put("Key", MD5Util.getMD5String(key));
//            }
//        }
//        return params;
//    }
//
//    public static void setCommonParams(JSONObject jsonObject) {
//        try {
//            jsonObject.put("app_version", "4.6.0");
//            jsonObject.put("app_from_type", 2);
//            jsonObject.put("app_version_mark","4.6.0");
//            jsonObject.put("app_version_code","4.6.0");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
