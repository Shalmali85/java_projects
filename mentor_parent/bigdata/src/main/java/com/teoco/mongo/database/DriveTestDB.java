package com.teoco.mongo.database;

import com.mongodb.*;

import java.util.Iterator;
import java.util.List;

/**
 * Created by roysha on 3/11/2016.
 */
public class DriveTestDB {

    public static void fetchDriveTestMetadata() {
        ParallelScanOptions parallelScanOptions = ParallelScanOptions
                .builder()
                .build();
        List<Cursor> dbCursor = DBConnection.getInstance().getCollectionName("drive_test_metadata").parallelScan(parallelScanOptions);
        for (Cursor cursor:dbCursor) {
            while (cursor.hasNext()){
                DBObject dbObject = cursor.next();

                    System.out.println("latitude---" + dbObject.get("Latitude"));

        }

        }
    }

    public static void fetchDriveTest() {
        DBCursor dbCursor = DBConnection.getInstance().getCollectionName("drive_test_metadata").find();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();


            if (dbObject.get("Pmch Bler") != null) {
                System.out.println("latitude---" + dbObject.get("Latitude"));
                BasicDBList list = (BasicDBList) dbObject.get("Pmch Bler");
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    Double dd = (Double) iterator.next();
                    System.out.println(dd);

                }


            }

        }
    }
}
