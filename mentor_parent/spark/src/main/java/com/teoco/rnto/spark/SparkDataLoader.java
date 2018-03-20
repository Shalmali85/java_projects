package com.teoco.rnto.spark;

import com.teoco.rnto.Rdd.DriveTestMetadataWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;
import org.apache.phoenix.mapreduce.PhoenixInputFormat;
import org.apache.phoenix.mapreduce.PhoenixOutputFormat;

import org.apache.phoenix.mapreduce.util.PhoenixConfigurationUtil;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Created by shalmali on 14/8/16.
 */
public class SparkDataLoader {
    private static final String SPARK_MASTER_URL     = "local[*]";

    private static final String ZOOKEEPER_QUORUM_URL = "localhost:2181";

    public void loadData() {
    }

            public static void main (String[] args) throws IOException, SQLException {
                final SparkConf sparkConf = new SparkConf()
                        .setAppName("phoenix-spark")
                        .set("spark.executor.memory", "2g")
                        .setMaster(SPARK_MASTER_URL);

                SparkContext jsc = new SparkContext(sparkConf);
//List list=new ArrayList()
                final Configuration configuration = HBaseConfiguration.create();
                configuration.set(HConstants.ZOOKEEPER_QUORUM, ZOOKEEPER_QUORUM_URL);
                PhoenixConfigurationUtil.setInputTableName(configuration , "TEST.DRIVE_TEST_METADATA");
                PhoenixConfigurationUtil.setOutputTableName(configuration , "TEST.DRIVE_TEST_METADATA");
                PhoenixConfigurationUtil.setInputQuery(configuration,PhoenixConfigurationUtil.getUpsertStatement(configuration));
                PhoenixConfigurationUtil.setInputClass(configuration, DriveTestMetadataWritable.class);

                configuration.setClass(JobContext.OUTPUT_FORMAT_CLASS_ATTR,PhoenixOutputFormat.class, OutputFormat.class);
               // jsc.parallelize(list).save

             //   JavaPairRDD<NullWritable, DriveTestMetadataWritable> driveTestMetadataWritableJavaPairRDD = jsc.newAPIHadoopRDD(configuration, PhoenixInputFormat.class, NullWritable.class,DriveTestMetadataWritable.class);
              //  JavaRDD<String> input=jsc.hadoopFile()..parallelize().textFile("/home/shalmali/driveTestMetadata_1447321200000");


             /*   driveTestMetadataWritableJavaPairRDD.mapToPair(new PairFunction<Tuple2<NullWritable,DriveTestMetadataWritable>,NullWritable,DriveTestMetadataWritable> () {

                    @Override
                    public Tuple2<NullWritable, DriveTestMetadataWritable> call(Tuple2 tuple) throws Exception {
                        final DriveTestMetadataWritable stockWritable = (DriveTestMetadataWritable)tuple._2;
                        final DriveTestMetadata bean = stockWritable.getStockBean();
                        double[] recordings = bean.getRecordings();
                        double sum = 0.0;
                        for(double recording: recordings) {
                            sum += recording;
                        }
                        double avg = sum / recordings.length;
                        bean.setAverage(avg);
                        stockWritable.setStockBean(bean);
                        return new Tuple2<NullWritable, StockWritable>(NullWritable.get(), stockWritable);
                    }
                }).saveAsNewAPIHadoopDataset(configuration);

                jsc.stop();
            }*/
    }
}
