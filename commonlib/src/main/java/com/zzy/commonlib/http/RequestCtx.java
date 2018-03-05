package com.zzy.commonlib.http;

import android.text.TextUtils;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

//请求上下文
public class RequestCtx {
    private final String url;
    private final String method;
    private final LinkedHashMap<String, String> params;
    private final String body;
    private final String contentType;
    private final HInterface.JsonParser jsonParser;
    private final HInterface.Validator validator;
    private final HInterface.DataCallback callback;
    private final Map<String, String> headerMap;
    private final HInterface.Decrypter decrypter;
    private final int timerout;
    private final Object tagObj;

    public RequestCtx(Builder b) {
        url = b.url;
        method = b.method;
        params = b.params;
        body = b.body;
        contentType = b.contentType;
        jsonParser = b.jsonParser;
        validator = b.validator;
        callback = b.callback;
        headerMap = b.headerMap;
        decrypter = b.decrypter;
        timerout = b.timerout;
        tagObj = b.tagObj;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public LinkedHashMap<String, String> getParams() {
        return params;
    }

    public String getBody() {
        return body;
    }

    public String getContentType() {
        return contentType;
    }

    public HInterface.JsonParser getJsonParser() {
        return jsonParser;
    }

    public HInterface.Validator getValidator() {
        return validator;
    }

    public HInterface.DataCallback getCallback() {
        return callback;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public HInterface.Decrypter getDecrypter() {
        return decrypter;
    }

    public int getTimerout() {
        return timerout;
    }

    public Object getTagObj() {
        return tagObj;
    }

    public static class Builder {
        private String url;
        private String method;
        private LinkedHashMap<String, String> params;
        private String body;
        private String contentType;
        private HInterface.JsonParser jsonParser;
        private HInterface.Validator validator;
        private HInterface.DataCallback callback;
        private Map<String, String> headerMap;
        private HInterface.Decrypter decrypter;
        private int timerout;
        private Object tagObj;

        public Builder(String url) {
            this.url = url;
        }

        public Builder method(String val) {
            method = val;
            return this;
        }

        public Builder params(LinkedHashMap<String, String> val){
            params = val;
            this.url = jointUrl(params, url);
            return this;
        }

        public Builder body(String val) {
            body = val;
            return this;
        }

        public Builder contentType(String val) {
            contentType = val;
            return this;
        }

        public Builder jsonParser(HInterface.JsonParser val) {
            jsonParser = val;
            return this;
        }

        public Builder validator(HInterface.Validator val) {
            validator = val;
            return this;
        }

        public Builder callback(HInterface.DataCallback val) {
            callback = val;
            return this;
        }

        public Builder headerMap(Map<String, String> val) {
            headerMap = val;
            return this;
        }

        public Builder decrypter(HInterface.Decrypter val) {
            decrypter = val;
            return this;
        }

        public Builder timerout(int val) {
            timerout = val;
            return this;
        }

        public Builder tagObj(Object val) {
            tagObj = val;
            return this;
        }

        public RequestCtx build() {
            return new RequestCtx(this);
        }
    }

    private static String jointUrl(LinkedHashMap<String, String> params, String url) {
        try {
            // 添加url参数
            if (params != null) {
                Iterator<String> it = params.keySet().iterator();
                StringBuffer sb = new StringBuffer();
                while (it.hasNext()) {
                    String key = it.next();
                    String value = params.get(key);
                    if(TextUtils.isEmpty(sb.toString()))
                    {
                        sb.append("?");
                    } else {
                        sb.append("&");
                    }
                    sb.append(key);
                    sb.append("=");
                    sb.append(value);
                }
                url += sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
}
