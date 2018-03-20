package com.teoco.mongo.database;

import com.google.common.base.Joiner;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat;

import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.phoenix.mapreduce.CsvToKeyValueMapper;
import org.apache.phoenix.util.CSVCommonsLoader;
import org.apache.phoenix.util.ColumnInfo;
import java.io.File;
import java.util.*;



/**
 * Created by roysha on 4/10/2016.
 */
public class HbaseLoader extends Configured implements Tool {
    private static Configuration config = null;

    @Override
    public int run(String[] strings) throws Exception {
        Path inputPath = new Path(strings[0]);
        Path outputPath = new Path(strings[1]);
        Job job = new Job(config);
        TextInputFormat.addInputPath(job, inputPath);
        job.setInputFormatClass(TextInputFormat.class);
        TextOutputFormat.setOutputPath(job, outputPath);
        job.setJarByClass(CsvToKeyValueMapper.class);
        job.setMapperClass(CsvToKeyValueMapper.class);
        job.setOutputFormatClass(TableOutputFormat.class);
        job.setMapOutputKeyClass(ImmutableBytesWritable.class);
        job.setMapOutputValueClass(KeyValue.class);

        HTable hTable = new HTable(config,strings[2]);

        HFileOutputFormat.configureIncrementalLoad(job, hTable);
        System.out.println("Start loading into database");
        boolean isSuccess = job.waitForCompletion(true);
        LoadIncrementalHFiles loadFile = null;
        if (isSuccess) {
            try {

                loadFile = new LoadIncrementalHFiles(config);
               loadFile.doBulkLoad(outputPath, hTable);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Succesfully loaded into database");
            FileUtils.deleteDirectory(new File(strings[1]));
        }
        return 1;
    }

    public void uploadToDatabase(String tableName, String inputFile, String outputFile) throws Exception {
        String[] args = new String[3];
        args[0] = inputFile;
        args[1] = outputFile;
        args[2] = tableName;
        config = HBaseConfiguration.create();
       // config.set(CsvToKeyValueMapper.TABLE_NAMES_CONFKEY, tableName);
        config.set(CsvToKeyValueMapper.TABLE_NAME_CONFKEY, tableName);
        config.set("phoenix.mapreduce.import.quotechar", "'");
        config.set("phoenix.mapreduce.import.escapechar", "|");

        config.set(CsvToKeyValueMapper.FIELD_DELIMITER_CONFKEY, ",");
        List<ColumnInfo> columnInfoList= CSVCommonsLoader.generateColumnInfo(DBConnection.getConnectionForPhoenix(), tableName, null, true);
        String columnList= Joiner.on("|").useForNull("").join(columnInfoList);
        System.out.println("the string---+"+columnList);
        config.set(CsvToKeyValueMapper.COLUMN_INFO_CONFKEY,columnList);
         ToolRunner.run(config, new HbaseLoader(), args);
        DBConnection.closeConnectionForPhoenix();
    }

public static void main(String args[]){
    HbaseLoader hbaseLoader=new HbaseLoader();
    try {
        hbaseLoader.uploadToDatabase("TEST.DRIVE_TEST_METADATA","/home/shalmali/","/home/shalmali/output_hadoop/"+UUID.randomUUID().toString());//d780f5eb-451b-4581-884a-947bf55f2116
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}