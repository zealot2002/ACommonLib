package com.zzy.acommonlib;

import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.http.HProxy;
import com.zzy.commonlib.http.RequestCtx;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.zzy.commonlib.http.HConstant.HTTP_METHOD_GET;
import static com.zzy.commonlib.http.HConstant.HTTP_METHOD_POST;

/**
 * @author zzy
 * @date 2018/2/5
 */

public class TestHttp {
    public static void doTestGet() {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("city","北京");

        String url = "http://www.sojson.com/open/api/weather/json.shtml";

        RequestCtx ctx = new RequestCtx.Builder(url)
                .params(map)
                .method(HTTP_METHOD_GET)
                .callback(callback)
                .jsonParser(getDataJsonParser)
                .timerout(10*1000)
                .build();
        try {
            HProxy.getInstance().request(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void doTestPost(){
        String url = "http://10.100.19.167/csc-api";
        Map<String, String> headerMap = HUtils.getCommonHeader();
        String body = HUtils.getBody(1,"homePage");

        RequestCtx ctx = new RequestCtx.Builder(url)
                .method(HTTP_METHOD_POST)
                .headerMap(headerMap)
                .body("")
                .callback(callback)
                .jsonParser(getDataJsonParser)
                .timerout(10*1000)
                .build();
        try {
            HProxy.getInstance().request(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static HInterface.DataCallback callback = new HInterface.DataCallback() {
        @Override
        public void requestCallback(int result, Object data, Object tagData) {
            if (result == HConstant.SUCCESS) {

            }
        }
    };
    static HInterface.JsonParser getDataJsonParser = new HInterface.JsonParser() {
        @Override
        public Object[] parse(String str) throws JSONException {
            JSONTokener jsonParser = new JSONTokener(str);
            JSONObject obj = (JSONObject) jsonParser.nextValue();
            int errorCode = obj.getInt("err");

            return new Object[]{HConstant.SUCCESS, 1};
        }
    };
}
