package com.teoco.rnto.util;

import org.apache.commons.cli.*;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by roysha on 2/2/2016.
 */
public class ProcessFileOperation {
    private static final String FILE_TYPE = "fileType";
    private static final String DESTINATION_FOLDER = "output";
    private static final String SOURCE_FOLDER = "source";
    private static final String TIMEFROM = "timeFrom";
    private static final String TIMETO = "timeTo";
    private static final String THREADPOOLSIZE = "threadPoolSize";
    private static final String MAXALLOWEDTIME = "maxAllowedTime";

    public static void main(String args[]) throws Exception {
            long startTime=0;
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
                String fileType = properties.getProperty(FILE_TYPE);
                String timeFrom = properties.getProperty(TIMEFROM);
                String timeTo = properties.getProperty(TIMETO);
                String threadPoolSize = properties.getProperty(THREADPOOLSIZE);
                String maxAllowedTime = properties.getProperty(MAXALLOWEDTIME);

                 startTime=System.currentTimeMillis();

                List<File> existingZippedFiles = FileOperation.getFiles(sourceFolder, null);
                FileOperation.makeDirectoriesInPath(destinationFolder);
                ThreadExecutor threadExecutor= new ThreadExecutor(Integer.parseInt(threadPoolSize),Integer.parseInt(maxAllowedTime));
                for (File file : existingZippedFiles) {
                   if (file.getName().endsWith(".zip") || file.getName().endsWith(".tar")) {
                       ProcessFileRun fileRun = new ProcessFileRun(file, destinationFolder, Integer.parseInt(timeFrom), fileType, Integer.parseInt(timeTo));
                       threadExecutor.processFile(fileRun);
                   }

                }

                threadExecutor.shutdown();
            }
            long endTime=System.currentTimeMillis();
            System.out.print("Total time taken to extract all files--"+(endTime-startTime));


    }


}
