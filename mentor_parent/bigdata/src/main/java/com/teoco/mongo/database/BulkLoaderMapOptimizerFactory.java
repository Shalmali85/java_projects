package com.teoco.mongo.database;

import java.util.HashMap;

/**
 * Created by shalmali on 8/8/16.
 */
public class BulkLoaderMapOptimizerFactory {
private String databaseType=null;
    private HashMap<String,IBulkLoaderMapOptimizer> hmap;
    public IBulkLoaderMapOptimizer getBulkLoader(String databaseType){
        IBulkLoaderMapOptimizer iBulkLoaderMapOptimizer=null;
        iBulkLoaderMapOptimizer= hmap.get(databaseType);
        return iBulkLoaderMapOptimizer;
    }
    public BulkLoaderMapOptimizerFactory(String databaseType,HashMap<String,IBulkLoaderMapOptimizer> hmap){
        this.databaseType=databaseType;
        this.hmap=hmap;

    }
}
