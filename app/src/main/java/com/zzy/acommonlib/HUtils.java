package com.zzy.acommonlib;

import android.util.Log;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zzy
 * @date 2018/3/2
 */

public class HUtils {
    public static Map<String, String> getCommonHeader(){
        Map<String, String> headerMap = new LinkedHashMap<String, String>();
        headerMap.put("Content-Type","text/plain");
        headerMap.put("device","android");
//        headerMap.put("token","");
        Log.e("zzy","header:"+headerMap.toString());
        return headerMap;
    }

    public static String getBody(int pageNum,String action) {
        StringBuilder sb = new StringBuilder("{\"apiName\": \"getAppPageData\",\n" +
                "        \"sysName\": \"cf\",\n" +
                "        \"platform\": \"app\",\n" +
                "        \"data\": {");
        sb.append("\"pageNum\":"+pageNum+",");
        sb.append("\"pageType\":\""+action+"\"");
        sb.append("}}");

        Log.e("zzy","body:"+sb.toString());
        return sb.toString();
    }
}
