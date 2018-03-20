package com.teoco.rnto.Rdd;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.lib.db.DBWritable;
import org.apache.phoenix.mapreduce.PhoenixInputFormat;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shalmali on 14/8/16.
 */
public class DriveTestMetadataWritable implements DBWritable{


    public void write(PreparedStatement preparedStatement) throws SQLException {

    }

    public void readFields(ResultSet resultSet) throws SQLException {

    }


}
