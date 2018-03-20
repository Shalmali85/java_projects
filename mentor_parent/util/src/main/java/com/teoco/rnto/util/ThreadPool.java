package com.teoco.rnto.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Manish on 16-09-2014.
 */
public class ThreadPool {
    private static Logger logger = LogManager.getLogger(ThreadPool.class);
    public static final int DEFAULT_POOL_SIZE = 50;
    private static ThreadPool _instance;

    private final ExecutorService executorService;
    //public static final ArrayList<Future> futures = new ArrayList<Future>();
    public static final Map<String,ArrayList<Future>> snapShotFutures = new HashMap();;

    private ThreadPool(int poolSize) {
        executorService = Executors.newFixedThreadPool(poolSize);
    }

    /**
     * Only one instance in WebSerice or Agent throughpout its lifetime
     * Webservices : shuts down this in SigmaWebserviceStartupBean.terminate
     * @return
     */

    public static synchronized ThreadPool getInstance() {
        if(_instance == null) {
            logger.info("Threadpool created");
            _instance = new ThreadPool(DEFAULT_POOL_SIZE);
        }
        return _instance;
    }

    public boolean runAsync(Runnable r, String key) {
       synchronized(snapShotFutures) { //java collections are not thread safe
           Future future = executorService.submit(r);
           synchronized (snapShotFutures) {
               //futures.add(future);
               if (key != null) {
                   if (snapShotFutures.containsKey(key)) {
                       snapShotFutures.get(key).add(future);
                   }
               }
           }
       }
        return true;
    }
    public synchronized void startSnapShot(String key)
    {
        if(snapShotFutures.containsKey(key)) {
            synchronized (snapShotFutures) {
                snapShotFutures.get(key).clear();
            }
        }
        else{
            synchronized (snapShotFutures) {
                snapShotFutures.put(key, new ArrayList<Future>());
            }
        }
    }


    public void waitTillCompletionSnapShot(String key) throws ExecutionException, InterruptedException{
       try {
            synchronized (snapShotFutures) {
                if(snapShotFutures.containsKey(key)) {
                    for (Future future : snapShotFutures.get(key)) {
                        future.get();
                    }
                }
            }
        }
        catch(ConcurrentModificationException cex){
            logger.error(cex.getMessage(),cex );
        }
        finally{
           synchronized(snapShotFutures) {
                snapShotFutures.remove(key);
           }
       }
    }

    /*public void waitTillCompletion() throws ExecutionException, InterruptedException {
        try {
            for (Future future : futures) {
                future.get();
                //todo : We should give it some sleep(3000) ?
            }
        }
        catch(ConcurrentModificationException cex){
            logger.error(cex.getMessage(),cex );
        }
    }

    public boolean isComplete() {
        for (Future future : futures) {
            if (!future.isDone() && !future.isCancelled()) return false;
        }
        return true;
    }*/

    public void shutdown() {
        executorService.shutdownNow();
        //futures.clear();
        synchronized (snapShotFutures) {
            snapShotFutures.clear();
        }
        _instance = null;
        logger.info("Threadpool shutdwon completed");
    }

}
