package com.teoco.mongo.database;

import com.teoco.mongo.entity.DriveTestMetadata;
import com.teoco.mongo.rop.RopPeriod;
import com.teoco.mongo.services.DriveTestKey;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shalmali on 8/8/16.
 */
public interface IBulkLoaderMapOptimizer  {
     HashMap<DriveTestKey, List<DriveTestMetadata>> optimizeMap(HashMap<DriveTestKey, List<DriveTestMetadata>> hashMapDriveTestData,RopPeriod rop) throws IOException;
     void writeToDocumentorFile(HashMap<DriveTestKey, List<DriveTestMetadata>> hashMapDriveTestTempData,int fileId,RopPeriod rop) throws IOException;
}
