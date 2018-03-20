package com.teoco.mongo.rop;

import com.teoco.mongo.database.HbaseLoader;
import com.teoco.mongo.entity.DriveTestMetadata;
import com.teoco.mongo.util.CsvRecordWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by roysha on 4/10/2016.
 */
public class RopCsvManager {
    private String DRIVETESTPATH="/home/shalmali/driveTestMetadata_";
    private String OUTPUTPATH="/home/shalmali/output_hadoop/";




    private static RopCsvManager instance=null;
    FileWriter writer=null;
    private RopCsvManager(){

    }

    public static RopCsvManager getInstance(){
        if(instance==null)
            instance=new RopCsvManager();
        return  instance;
    }

    public void createCsv(RopPeriod rop){
        try {
             writer=new FileWriter(DRIVETESTPATH+rop.getRopStartTimeInMs());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void getCsv(Object obj){
        if(obj instanceof DriveTestMetadata) {
            try {
                writer.write(CsvRecordWriter.getInstance().toCSV((DriveTestMetadata) obj));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeCsv(RopPeriod rop,String table){
        try {
            writer.close();
            HbaseLoader hbaseLoader=new HbaseLoader();
            try {
                hbaseLoader.uploadToDatabase(table,DRIVETESTPATH+rop.getRopStartTimeInMs(),OUTPUTPATH+ UUID.randomUUID());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
