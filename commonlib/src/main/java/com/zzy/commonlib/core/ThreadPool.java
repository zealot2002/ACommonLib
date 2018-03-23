package com.zzy.commonlib.core;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author zzy
 * @date 2017/12/12
 */

public class ThreadPool {
    final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    final int KEEP_ALIVE_TIME = 1;
    final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private static final ThreadPool ourInstance = new ThreadPool();

    private ExecutorService pool;
    public static ThreadPool getInstance() {
        return ourInstance;
    }

    private ThreadPool() {
        pool = new ThreadPoolExecutor(NUMBER_OF_CORES,
                NUMBER_OF_CORES*4,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                new LinkedBlockingDeque<Runnable>(),
                new ThreadPoolExecutor.AbortPolicy());
    }
    public ExecutorService getPool(){
        return pool;
    }
    public void shutDown(){
        pool.shutdown();
    }
}
