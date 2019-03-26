package com.zzy.commonlib.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetryInterceptor implements Interceptor {

    private int maxRetry;
    private int retryNum = 0;

    public RetryInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        try{
            Request request = chain.request();
            //System.out.println("retryNum=" + retryNum);
            Response response = chain.proceed(request);
            while (!response.isSuccessful() && retryNum < maxRetry) {
                retryNum++;
                //System.out.println("retryNum=" + retryNum);
                response = chain.proceed(request);
            }
            return response;
        }catch(Exception e){
            e.printStackTrace();
        }
        return chain.proceed(chain.request());
    }
}