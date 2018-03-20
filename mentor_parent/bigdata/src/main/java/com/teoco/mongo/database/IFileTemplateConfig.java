package com.teoco.mongo.database;

import com.teoco.mongo.entity.FileTemplateConfiguredColumn;

import java.util.Map;

/**
 * Created by shalmali on 8/8/16.
 */
public interface IFileTemplateConfig {
     Map<String, Map<String, FileTemplateConfiguredColumn>> fetchFileTemplates();
     Map<String, FileTemplateConfiguredColumn> fetchFileType(String fileName,Map<String, Map<String, FileTemplateConfiguredColumn>> fileTemplateMap);

    }
