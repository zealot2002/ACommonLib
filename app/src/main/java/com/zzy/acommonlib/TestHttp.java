package com.zzy.acommonlib;

import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.http.HProxy;
import com.zzy.commonlib.http.RequestCtx;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.LinkedHashMap;

import static com.zzy.commonlib.http.HConstant.HTTP_METHOD_GET;

/**
 * @author zzy
 * @date 2018/2/5
 */

public class TestHttp {
    public static void doTest(){
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("city","北京");

        String url = "http://www.sojson.com/open/api/weather/json.shtml";

        RequestCtx ctx = new RequestCtx.Builder(map)
                .methodAndUrl(HTTP_METHOD_GET, url)
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
