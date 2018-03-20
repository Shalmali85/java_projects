package com.teoco.rnto.util.peformance;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.map.hash.TObjectLongHashMap;

import java.util.UUID;

/**
 * Created by guptaam on 3/20/2015.
 * Generic class to be used, to compute time elapsed for various operations in the code
 *
 * @author Shyam Implemented utility methods for measuring parallel processes
 */
public class TimeSpan {

    static TObjectLongHashMap<String> startTime = new TObjectLongHashMap<String>();
    static TObjectLongHashMap<String> endTime = new TObjectLongHashMap<String>();
    static TObjectLongHashMap<String> accumulatedTime = new TObjectLongHashMap<String>();
    static TObjectIntHashMap<String> parallelCounts = new TObjectIntHashMap<String>();

    /**
     * Start recording the time at the beginning of an operation , using a unique identifier
     * @param key
     */
    public static  String start(String key){
        if(key == null){
            key = UUID.randomUUID().toString();
        }
        startTime.put(key, System.currentTimeMillis());
        return key;
    }

    /**
     * End recording the time at the end of an operation , using a unique identifier
     * @param Key
     * @return false if key is null or there is no operation associated with this Key
     */
    public static  boolean end(String Key){
        if(Key == null){
            return false;
        }
        if(!startTime.containsKey(Key)){
            return false;
        }
        endTime.put(Key, System.currentTimeMillis());
        return true;
    }

    /**
     * Initiate time accumulation
     * @param key
     * @return
     */
    public static  String accumulate_start(String key){
        return start(key);
    }

    /**
     * Accumulate time intervals to sum up time spent in a repeated operation
     * @param key
     * @return
     */
    public static  boolean accumulate_end(String key){
        if(key == null){
            return false;
        }
        if(!end(key)){
            return false;
        }
        long difference = endTime.get(key) - startTime.get(key);
        if(accumulatedTime.containsKey(key)){
            long tm = accumulatedTime.get(key);
            accumulatedTime.put(key,  tm + difference);
        }
        else {
            accumulatedTime.put(key,  difference);
        }
        return true;
    }

    /**
     * Initiate time monitoring for parallel processes.
     * <p>
     * e.g.
     * <p>
     * ....|10:11:00  |10:11:10  |10:11:20  |10:11:30  |10:11:40  |10:11:50  |10:12:00
     * A:  |---------------------|
     * B:       |-----------|
     * C:        |--------------------------|
     * D:                                              |---------------------|
     * ....|----------30 seconds -----------|          |------20 seconds-----|
     * Total Time: 30+20 = 50 seconds
     *
     * @param key
     * @return
     */
    public static synchronized String parallel_accumulate_start(String key) {
        int count = parallelCounts.adjustOrPutValue(key, 1, 1);
        if (count == 1) return accumulate_start(key); // new or accumulative parallel
        else return key; // there are unfinished parallels who have started already
    }

    /**
     * Update time monitored for parallel processes.
     *
     * @param key
     * @return
     * @see #parallel_accumulate_start(String)
     */
    public static synchronized boolean parallel_accumulate_end(String key) {
        if (key == null || !parallelCounts.containsKey(key) || !end(key)) return false;

        parallelCounts.adjustValue(key, -1);
        return accumulate_end(key);
    }

    /**
     * Utility method to check if any parallel processes measuring time using
     * {@link #parallel_accumulate_start(String)} and {@link #parallel_accumulate_end(String)}
     * are still running.
     *
     * @param key
     * @return
     * @see #parallel_accumulate_start(String)
     */
    public static synchronized boolean parallel_hasRunningProcesses(String key) {
        return parallelCounts.containsKey(key) && parallelCounts.get(key) > 0;
    }

    /**
     * Whether the operation was started or not.
     * @param key
     * @return
     */
    public static boolean hasStarted(String key) {
        return startTime.containsKey(key);
    }

    /**
     * Whether the operation has ended or not.
     * @param key
     * @return
     */
    public static boolean hasEnded(String key) {
        return endTime.containsKey(key);
    }

    /**
     * Returns formatted string for elapsed time for a given operation
     * @param key
     * @param message
     * @return
     */
    public static  String toString(String key, String message ){
        if(key == null){
            return "Time span cannot be determined for null Key";
        }
        if(!hasStarted(key)){
            return "There is no operation started with this Key";
        }
        if(!hasEnded(key)){
            return "There is no operation end with this Key";
        }
        long difference = endTime.get(key) - startTime.get(key);
        return String.format("TIME SPAN for %s  = %.2f s" , message , difference/1000.0);
    }

    /**
     * Return accumulated time
     * @param key
     * @param message
     * @return
     */
    public static String toStringAccumulatedTime(String key, String message ){
        if(key == null){
            return "Time span cannot be determined for null Key";
        }
        if(!accumulatedTime.containsKey(key)){
            return "There is no operation started with this Key";
        }
        return String.format("TIME SPAN for %s  = %.2f s" , message , accumulatedTime.get(key)/1000.0);
    }
}
