package com.teoco.rnto.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by roysha on 2/9/2016.
 */
public class ThreadExecutor {
    private static ExecutorService executor = null;
    public  int poolSize;
    public  int maxTime;

    public ThreadExecutor(int poolSize,int maxTime){
        this.poolSize=poolSize;
        this.maxTime=maxTime;
        executor=Executors.newFixedThreadPool(poolSize);
    }

    public <Obj extends  Runnable> void  processFile(Obj obj){
        executor.execute(obj);
    }

    public  void shutdown(){
        executor.shutdown();
        try {
            executor.awaitTermination(maxTime, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
