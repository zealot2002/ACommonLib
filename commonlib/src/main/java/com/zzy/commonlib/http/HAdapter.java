package com.zzy.commonlib.http;

import android.text.TextUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*http 适配层 :  okhttp in use*/
public class HAdapter {
//    public static final MediaType CONTENT_TYPE = MediaType.parse("text/plain");

/*******************************************************************************************************/
    public static String sendGetRequest(RequestCtx ctx) throws Exception {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(ctx.getTimerout(), TimeUnit.SECONDS)
                .readTimeout(ctx.getTimerout(), TimeUnit.SECONDS)
                .writeTimeout(ctx.getTimerout(),TimeUnit.SECONDS)
                .build();
        Request request = null;

        if(ctx.getHeaderMap()!=null){
            request = new Request.Builder()
                    .url(ctx.getUrl())
                    .headers(Headers.of(ctx.getHeaderMap()))
                    .build();
        }else{
            request = new Request.Builder()
                    .url(ctx.getUrl())
                    .build();
        }

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);

        return handleServerData(response.body().string(),response.code(),ctx);
    }

    public static String sendPostRequest(RequestCtx ctx) throws Exception {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(ctx.getTimerout(), TimeUnit.SECONDS)
                .readTimeout(ctx.getTimerout(), TimeUnit.SECONDS)
                .writeTimeout(ctx.getTimerout(),TimeUnit.SECONDS)
                .build();

        Request request;
        if(ctx.getHeaderMap()!=null){
            request = new Request.Builder()
                    .url(ctx.getUrl())
                    .headers(Headers.of(ctx.getHeaderMap()))
                    .post(RequestBody.create(MediaType.parse(ctx.getContentType()),ctx.getBody().toString()))
                    .build();
        }else{
            request = new Request.Builder()
                    .url(ctx.getUrl())
                    .post(RequestBody.create(MediaType.parse(ctx.getContentType()),ctx.getBody().toString()))
                    .build();
        }
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);

        return handleServerData(response.body().string(),response.code(),ctx);
    }

    private static String handleServerData(String strResult, int statusCode, RequestCtx ctx){
        /** 服务器返回数据 **/
        if(strResult.contains(HConstant.HTML_DATA_PRE)){
            return HConstant.HTML_DATA_ERROR;
        }
        if(TextUtils.isEmpty(strResult)||strResult.equals("null")){
            return HConstant.EMPTY_DATA_ERROR;
        }
        if (statusCode == 200) {
            /** 解密数据 **/
            if(ctx.getDecrypter()!=null){
                return ctx.getDecrypter().decrypt(strResult);
            }
            return strResult;
        } else {
            return HConstant.HTTP_ERROR + statusCode;
        }
    }
}