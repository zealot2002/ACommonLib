package com.zzy.commonlib.http;

import android.text.TextUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*http 适配层 :  okhttp in use*/
public class HAdapter {
//    public static final MediaType CONTENT_TYPE = MediaType.parse("text/plain");
    private static OkHttpClient okHttpClient;

/*******************************************************************************************************/
    public static Object[] sendGetRequest(RequestCtx ctx) throws Exception {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(ctx.getTimerout(), TimeUnit.SECONDS)
                .readTimeout(ctx.getTimerout(), TimeUnit.SECONDS)
                .writeTimeout(ctx.getTimerout(),TimeUnit.SECONDS)
                .addInterceptor(new RetryInterceptor(ctx.getRetryCount()))
                .build();
        Request request;

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

        return handleServerData(response.body().string(),response.code(),response.receivedResponseAtMillis(),ctx);
    }

    synchronized public static Object[] sendPostRequest(RequestCtx ctx) throws Exception {
        if(okHttpClient == null){
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(ctx.getTimerout(), TimeUnit.SECONDS)
                    .readTimeout(ctx.getTimerout(), TimeUnit.SECONDS)
                    .writeTimeout(ctx.getTimerout(),TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true)
//                .addInterceptor(new KeepAliveConfigInterceptor())
//                .connectionPool(new ConnectionPool(5,300,TimeUnit.SECONDS))
                    .addInterceptor(new RetryInterceptor(ctx.getRetryCount()))
                    .build();
        }
        Request request;
        if(ctx.getHeaderMap()!=null){
            request = new Request.Builder()
                    .url(ctx.getUrl())
                    .headers(Headers.of(ctx.getHeaderMap()))
                    .post(RequestBody.create(MediaType.parse(ctx.getContentType()),ctx.getBody()))
                    .build();
        }else{
            request = new Request.Builder()
                    .url(ctx.getUrl())
                    .post(RequestBody.create(MediaType.parse(ctx.getContentType()),ctx.getBody()))
                    .build();
        }
        Response response = okHttpClient.newCall(request).execute();

        long responseTime = Long.valueOf(response.header("X-Timestamp"));
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);

        return handleServerData(response.body().string(),response.code(),responseTime,ctx);
    }

    //Object[0] time
    //Object[1] response or errorCode
    private static Object[] handleServerData(String strResult, int statusCode, long receiveTime,RequestCtx ctx){
        /** 服务器返回数据 **/
        String ret = strResult;
        if (statusCode == 200) {
            /** 解密数据 **/
            if(ctx.getDecrypter()!=null){
                ret = ctx.getDecrypter().decrypt(strResult);
            }
            return new Object[]{receiveTime,ret};
        } else {
            return new Object[]{receiveTime,HConstant.HTTP_ERROR + statusCode};
        }
    }
}