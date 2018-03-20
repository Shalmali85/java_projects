package com.teoco.mongo.database;

import java.util.HashMap;

/**
 * Created by shalmali on 8/8/16.
 */
public class SuperFactory {

    public FileTemplateConfigFactory getFileTemplateType(String databaseType){
        FileTemplateConfigFactory fileTemplateConfigFactory=new FileTemplateConfigFactory(databaseType);
        return fileTemplateConfigFactory;
    }
    public BulkLoaderMapOptimizerFactory getBulkLoaderType(String databaseType, HashMap hmap){
        BulkLoaderMapOptimizerFactory bulkLoaderMapOptimizerFactory=new BulkLoaderMapOptimizerFactory(databaseType,hmap);
        return bulkLoaderMapOptimizerFactory;
    }

}
