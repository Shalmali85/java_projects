package com.teoco.mongo.services;


import com.teoco.mongo.database.*;
import com.teoco.mongo.rop.RopCsvManager;
import com.teoco.mongo.rop.RopPeriod;
import com.teoco.mongo.rop.RopPeriodCollection;
import com.teoco.rnto.util.FileOperation;
import com.teoco.rnto.util.ProcessFileRun;
import com.teoco.rnto.util.ThreadExecutor;
import org.apache.commons.cli.*;
import org.apache.commons.collections.map.AbstractHashedMap;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by roysha on 2/17/2016.
 */
public class DriveTestOperation {
    private static final String DESTINATION_FOLDER = "output";
    private static final String SOURCE_FOLDER = "source";
    private static final String THREADPOOLSIZE = "threadPoolSize";
    private static final String MAXALLOWEDTIME = "maxAllowedTime";
    private static final String DATABASETYPE = "databaseType";
    private static final String OPTIMIZERCLASS = "optimizerClass";
    private static final String MONGO_CONNECTION_USED = "mongoConnectionUsed";


    public static void main (String args[]) throws ParseException {
     if (args.length > 0) {
         Options options = new Options();
         Option property = OptionBuilder.withArgName("property=value")
                 .hasArgs(2)
                 .withValueSeparator()
                 .withDescription("use value for given property")
                 .create("D");

         options.addOption(property);
         CommandLineParser parser = new GnuParser();
         CommandLine cmd = parser.parse(options, args);
         Properties properties = cmd.getOptionProperties("D");
         String destinationFolder = properties.getProperty(DESTINATION_FOLDER);

         String sourceFolder = properties.getProperty(SOURCE_FOLDER);
         String databaseType = properties.getProperty(DATABASETYPE);
         String threadPoolSize = properties.getProperty(THREADPOOLSIZE);
         String maxAllowedTime = properties.getProperty(MAXALLOWEDTIME);
         String optmizerClassName=properties.getProperty(OPTIMIZERCLASS) ;
         String mongoConnectionName=properties.getProperty(MONGO_CONNECTION_USED) ;
         HashMap hashmap=new HashMap<String, IBulkLoaderMapOptimizer >();
         try {
             hashmap.put(databaseType,Class.forName(optmizerClassName).newInstance());
         } catch ( InstantiationException e ) {
             e.printStackTrace();
         } catch ( IllegalAccessException e ) {
             e.printStackTrace();
         } catch ( ClassNotFoundException e ) {
             e.printStackTrace();
         }
         List<File> existingFiles = FileOperation.getFiles(sourceFolder, null);
         ThreadExecutor threadExecutor = new ThreadExecutor(Integer.parseInt(threadPoolSize), Integer.parseInt(maxAllowedTime));
         RopPeriodCollection rv = DriveTestParser.getFilesPerRopPeriods(existingFiles);
         DriveTestParser driveTestParser = new DriveTestParser();
         if("noSql".equals(databaseType))
         DBConnection.getInstance().getConnection(mongoConnectionName);
         RopPeriod ropPeriod=null;
         for (RopPeriod rop : rv.orderRop) {
             List<File> ropfileList = rv.mapRop.get(rop);
             ropPeriod=rop;
             if("Phoenix".equals(databaseType)) {
                 RopCsvManager.getInstance().createCsv(rop);
             }
             for (File file : ropfileList) {
                 try {
                     driveTestParser = new DriveTestParser(sourceFolder, destinationFolder, file.getAbsolutePath(),databaseType);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                 threadExecutor.processFile(driveTestParser);
             }

         }
         threadExecutor.shutdown();
         driveTestParser.optimizeMap(hashmap);
         if("noSql".equals(databaseType)) {
             try {
                 DBConnection.getInstance().equals(mongoConnectionName);
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }


     }

 }
}
