package com.teoco.mentor;


import org.apache.commons.cli.*;



import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


/**
 * Created by roysha on 5/17/2016.
 */
public class FileWatcher {
    private static final String SOURCE_FOLDERS = "source_folders";
    private static final String TIME = "time";

    /**
     * This function picks up the latest modified folder and checks whether the folder is up to date depedding on the file polling time.If the folder is up to date it does not restart the NSN Collector Service .If it is not up to date then it restarts the Nsn C
     * @param source
     * @param filePollingTime
     */
    public void getLatestFolder(String source,int filePollingTime){
        String[] sourceFolders = source.split(",");
        String[] command_stop = {"cmd.exe", "/c", "net", "stop", "NSN Collector Service"};
        String[] command_start = {"cmd.exe", "/c", "net", "start", "NSN Collector Service"};
        int count=1;
        List<String[]> list =new ArrayList<String[]>();
        list.add(command_stop);
        list.add(command_start);
        Process process;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        File[] files ;
        List<FileModified> fileModifiedArrayList;

        FileModified fileModified;
        boolean isMultipleFolder=false;
        if (sourceFolders == null)
            sourceFolders[0] = source;
        for (int i = 0; i < sourceFolders.length; i++) {
            File dir = new File(sourceFolders[i]);
            if (dir.isDirectory()) {
                files = dir.listFiles();
                fileModifiedArrayList = new ArrayList<FileModified>();
                for (int j = 0; j < files.length; j++) {
                    fileModified= new FileModified();
                    fileModified.setFile(files[j]);
                    fileModifiedArrayList.add(fileModified);

                }
                Collections.reverse(fileModifiedArrayList);
                if (System.currentTimeMillis() -  fileModifiedArrayList.get(0).getFile().lastModified() > filePollingTime * 60 * 1000) {
                    if(sourceFolders.length == 1 || !isMultipleFolder) {
                        for (String[] str : list) {
                            try {
                                process = new ProcessBuilder(str).start();
                                inputStream = process.getInputStream();
                                inputStreamReader = new InputStreamReader(inputStream);
                                bufferedReader = new BufferedReader(inputStreamReader);
                                String line;
                                while ((line = bufferedReader.readLine()) != null) {
                                    System.out.println(line);
                                }
                                if (list.size() != count)
                                    Thread.sleep(60000);
                            } catch (Exception ex) {
                                System.out.println("Exception : " + ex);
                            }
                            count++;
                            isMultipleFolder=true;
                        }
                    }



                }
                else
                {
                    System.out.println("The folder is up to date ");

                }
            }
        }

    }

    public static void main(String args[]) throws Exception {
        if (args.length > 0) {
            Options options = new Options();
            Option property = OptionBuilder.withArgName("property=value")
                    .hasArgs(2)
                    .withValueSeparator()
                    .withDescription("use value for given property")
                    .create("D");

            options.addOption(property);
            CommandLineParser parser = new GnuParser();
            CommandLine cmd = null;
            try {
                cmd = parser.parse(options, args);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Properties properties = cmd.getOptionProperties("D");
            String source = properties.getProperty(SOURCE_FOLDERS);
            String polling_time = properties.getProperty(TIME);
            int filePolling = Integer.parseInt(polling_time);
            FileWatcher fileWatcher=new FileWatcher();
            fileWatcher.getLatestFolder(source,filePolling);

        }

    }


}
