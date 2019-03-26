package com.zzy.commonlib.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class KeepAliveConfigInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request()
                .newBuilder()
                .addHeader("Connection","close");
        return chain.proceed(builder.build());
    }
}