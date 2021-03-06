package com.zzy.commonlib.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.zzy.commonlib.core.ThreadPool;

import org.json.JSONException;
import java.io.IOException;


public class HProxy {
    private static final String TAG = "HProxy";
    private static class Holder {
        private final static HProxy instance = new HProxy();
    }
    private HProxy() {
    }
    public static HProxy getInstance() {
        return HProxy.Holder.instance;
    }

    public void request(RequestCtx ctx) throws Exception {
        final BaseHandler handler = new BaseHandler(ctx,Looper.getMainLooper());
        final BaseTask taskThread = new BaseTask(ctx, handler);
        ThreadPool.getInstance().getPool().execute(taskThread);
    }
    static class BaseHandler extends Handler {
        private RequestCtx ctx;
        public BaseHandler(RequestCtx ctx, Looper looper) {
            super(looper);
            this.ctx = ctx;
        }

        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            try {
                Object[] objs = (Object[])msg.obj;
                ctx.getCallback().requestCallback(msg.what,objs[1],objs[2]);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG,e.toString());
            }
        }
    }
    class BaseTask implements Runnable {
        private RequestCtx ctx;
        private Handler handler;

        public BaseTask(RequestCtx ctx,Handler handler) {
            this.ctx = ctx;
            this.handler = handler;
        }

        public void run() {
            Object[] responseObjs = null;
            Object[] objs = new Object[3]; //0:成功还是失败；1:数据；2:请求者携带tag
            objs[2] = ctx.getTagObj();
            int retState = HConstant.SUCCESS; //0:normal;    -1:exception;   2:intercepted
            try {
//                Log.e(TAG,"请求服务 url:"+ctx.getUrl());
                if(ctx.getMethod().equals(HConstant.HTTP_METHOD_GET)){
                    responseObjs = HAdapter.sendGetRequest(ctx);
                }else if(ctx.getMethod().equals(HConstant.HTTP_METHOD_POST)){
                    responseObjs = HAdapter.sendPostRequest(ctx);
                }/*else if(ctx.getMethod().equals(HConstant.HTTP_METHOD_PUT)){
                    retString = HAdapter.sendPutRequest(ctx);
                }else if(ctx.getMethod().equals(HConstant.HTTP_METHOD_DEL)){
                    retString = HAdapter.sendDelRequest(ctx);
                }*/
//                Log.e(TAG,"服务返回数据:"+retString);
//                if(ctx.getDecrypter()!=null){
//                    retString = ctx.getDecrypter().decrypt(retString);
//                }
                if(((String) responseObjs[1]).contains(HConstant.HTTP_ERROR)){
                    retState = HConstant.FAIL;
                    objs[1] = responseObjs[1];
                }else{
                    //拦截
                    if(ctx.getInterceptor()!=null
                            &&ctx.getInterceptor().intercept((long)responseObjs[0],
                                                (String)responseObjs[1],ctx.getTagObj())
                            ){
                            //被拦截直接返回
//                            handler.sendMessage(handler.obtainMessage(HConstant.INTERCEPTED, objs));
                            retState = HConstant.INTERCEPTED;
                            return;
                    }
                    //解析
                    if(ctx.getJsonParser()!=null){
                        Object[] tmpObjs = ctx.getJsonParser().parse((String) responseObjs[1]);
                        objs[0] = tmpObjs[0];
                        objs[1] = tmpObjs[1];  
                        if(objs[0].equals(HConstant.SUCCESS)){
                            if(ctx.getValidator()!=null){
                                ctx.getValidator().validate(objs[1]);
                            }
                        }else{
                            retState = (int) objs[0];
                        }
                    }
                }
            }catch(NetDataInvalidException ne){
                ne.printStackTrace();
                retState = HConstant.FAIL;
                objs[1] = ne.toString();
            }catch(JSONException je){
                je.printStackTrace();
                retState = HConstant.FAIL;
                objs[1] = je.toString();
            }catch(IOException ioe){
                ioe.printStackTrace();
                retState = HConstant.FAIL;
                objs[1] = ioe.toString();
            }catch (Exception e) {
                e.printStackTrace();
                retState = HConstant.FAIL;
                objs[1] = e.toString();
            }finally{
                if(ctx.getCallback()!=null){
                    handler.sendMessage(handler.obtainMessage(retState, objs));
                }
            }
        }
    }
}