package com.teoco.mongo.database;

import com.teoco.mongo.entity.FileTemplateConfiguredColumn;

import java.util.Map;

/**
 * Created by shalmali on 10/8/16.
 */
public  abstract class FileTemplateConfig  implements IFileTemplateConfig{


    public Map<String, FileTemplateConfiguredColumn> fetchFileType(String fileName, Map<String, Map<String, FileTemplateConfiguredColumn>> fileTemplateMap) {
        for (Map.Entry<String, Map<String, FileTemplateConfiguredColumn>> entry : fileTemplateMap.entrySet()) {
            if (fileName.contains(entry.getKey().toString()))
            {
                return entry.getValue();
            }

        }
        return  null;
    }
}
