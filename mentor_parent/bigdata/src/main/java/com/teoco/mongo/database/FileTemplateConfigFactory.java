package com.teoco.mongo.database;

/**
 * Created by shalmali on 8/8/16.
 */
public class FileTemplateConfigFactory {
    private String databaseType=null;
    public IFileTemplateConfig getFileTemplateConfig(String databaseType){
        IFileTemplateConfig iFileTemplateConfig=null;
        if("noSql".equalsIgnoreCase(databaseType))
        {
             iFileTemplateConfig=new FileTemplateConfigForNoSql();
        }
        if("Phoenix".equalsIgnoreCase(databaseType))
        {
             iFileTemplateConfig=new FileTemplateConfigForPhoenix();

        }
          return iFileTemplateConfig;
    }

    public FileTemplateConfigFactory(String databaseType){
        this.databaseType=databaseType;
    }
}
