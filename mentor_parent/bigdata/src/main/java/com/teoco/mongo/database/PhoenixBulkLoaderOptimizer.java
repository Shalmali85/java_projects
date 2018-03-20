package com.teoco.mongo.database;

import com.teoco.mongo.entity.DriveTestMetadata;
import com.teoco.mongo.rop.RopCsvManager;
import com.teoco.mongo.rop.RopPeriod;
import com.teoco.mongo.services.DriveTestKey;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by shalmali on 8/8/16.
 */
public class PhoenixBulkLoaderOptimizer implements IBulkLoaderMapOptimizer{
    public static String deviceName=null;


    @Override
    public HashMap<DriveTestKey, List<DriveTestMetadata>> optimizeMap(HashMap<DriveTestKey, List<DriveTestMetadata>> hashMapDriveTestData,RopPeriod rop) throws IOException  {
        Iterator<Map.Entry<DriveTestKey, List<DriveTestMetadata>>> iterator = hashMapDriveTestData.entrySet().iterator();

            while (iterator.hasNext()) {
                DriveTestKey key = iterator.next().getKey();
                for (Map.Entry<DriveTestKey, List<DriveTestMetadata>> entry : hashMapDriveTestData.entrySet()) {
                    if (key.getLatitude().equals(entry.getKey().getLatitude()) && key.getLongitude().equals(entry.getKey().getLongitude()) && key.getCellIdentity() != null && entry.getKey().getCellIdentity() == null && key.getTotalRlcDownlinkThroughput() == null && key.getTotalRlcUplinkThroughput() == null) {
                        for (DriveTestMetadata driveTestMetadata : entry.getValue()) {
                            driveTestMetadata.setCellIdentity(key.getCellIdentity());
                            driveTestMetadata.setPhysicalCellId(key.getPci());
                            driveTestMetadata.setDlearfcn(key.getDlEarfcn());
                        }
                        key.isParent = true;
                    }
                }

            }
            return hashMapDriveTestData;
            // handle transaction for Drive Test at the end of each rop period
        }



    @Override
    public void writeToDocumentorFile(HashMap<DriveTestKey, List<DriveTestMetadata>> hashMapDriveTestTempData, int fileId,RopPeriod rop) throws IOException {
        hashMapDriveTestTempData.entrySet().forEach(driveTestKey-> driveTestKey.getValue().forEach(driveTestMetadata->{
                if (deviceName != null)
                    driveTestMetadata.setDeviceName(deviceName);
                if (!driveTestKey.getKey().isParent)
                    RopCsvManager.getInstance().getCsv(driveTestMetadata);
            }));
            // reinitialized after every Rop period
            hashMapDriveTestTempData = new HashMap<DriveTestKey, List<DriveTestMetadata>>();
            deviceName = null;
            RopCsvManager.getInstance().closeCsv(rop,"TEST.DRIVE_TEST_METADATA");


        }
}
