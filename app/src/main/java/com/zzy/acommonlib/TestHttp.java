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
        String url = "http://172.30.14.146:3000/homePage";
        RequestCtx ctx = new RequestCtx.Builder(url)
                .params(map)
                .method(HTTP_METHOD_GET)
                .contentType("")
                .callback(callback)
                .jsonParser(getDataJsonParser)
                .interceptor(interceptor)
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
                .body(body)
                .contentType("text/plain")
                .callback(callback)
                .jsonParser(getDataJsonParser)
                .interceptor(interceptor)
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

            }else if(result == HConstant.INTERCEPTED) {
                //do nothing
            }else{

            }
        }
    };
    static HInterface.JsonParser getDataJsonParser = new HInterface.JsonParser() {
        @Override
        public Object[] parse(String str) throws JSONException {
            JSONTokener jsonParser = new JSONTokener(str);
            JSONObject obj = (JSONObject) jsonParser.nextValue();
            int errorCode = obj.getInt("code");
            return new Object[]{HConstant.SUCCESS, 1};
        }
    };

    static HInterface.Interceptor interceptor = new HInterface.Interceptor() {

        @Override
        public boolean intercept(String str) throws Exception {
            JSONTokener jsonParser = new JSONTokener(str);
            JSONObject obj = (JSONObject) jsonParser.nextValue();
            int errorCode = obj.getInt("code");
            if(errorCode == 400){
                //
                return true;
            }
            return false;
        }
    };

}
