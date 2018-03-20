package com.teoco.rnto.util;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by IntelliJ IDEA.
 * User: guptaam
 * Date: 11/21/13
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileOperation {
    private static Logger logger = LogManager.getLogger();

    public static void listFiles(List<File> result, File dir, FileFilter filter) {
        assert dir.isDirectory() : "Path is not a directory : " + dir;
        File[] files = dir.listFiles();
        if (files == null) {
            return; // may be an IO error. see listFiles javadoc.
        }
        for (final File file : files) {
            if (file.isDirectory()) {
                listFiles(result, file, filter);
            } else if (filter == null || filter.accept(file)) {
                result.add(file);
            }
        }
    }

    /**
     * Get all files that ends with a given extension
     *
     * @param fileOrDirectoryName
     * @param s                   : Is the ending character sequence to search for. For example .ctum or .ctum.bin
     * @return
     */
    public static List<File> getFiles(String fileOrDirectoryName, final CharSequence s) {
        if (fileOrDirectoryName == null) return null;
        File dir = new File(fileOrDirectoryName);
        List<File> files = new ArrayList<File>();
        if (dir.isDirectory()) { //list of files

            FileFilter fileFilter = null;
            if (s != null) {
                fileFilter = new FileFilter() {
                    public boolean accept(File file) {
                        return file.getName().endsWith(s.toString());
                    }
                };
            }
            listFiles(files, dir, fileFilter);
        } else { //single file
            files.add(dir);
        }
        return files;
    }


    /**
     *This function iterates through entire unzipped file and extracts only thos file which is of specific file type (i.e MRO) and a specific time range (eg 2000 to 2145)
     * @param ae
     * @param ais
     * @param sourceFolder
     * @param timeFrom
     * @param timeTo
     * @param fileType
     * @throws IOException
     */
    public static void retriveSpecificFilesFromCompressedFolder(ArchiveEntry ae,ArchiveInputStream ais,String sourceFolder,int timeFrom ,int timeTo,String fileType,File file) throws IOException {
        int BUFFER_SIZE = 4096 * 4096;
        byte[] buffer = new byte[BUFFER_SIZE];
        File newFile = null;
        /*if (ae.isDirectory()) {
            String fileName = ae.getName();
            newFile = new File(sourceFolder + File.separator + fileName);
            System.out.println("file unzip : " + newFile.getAbsoluteFile());
            newFile.mkdirs();
        }*/ if (!ae.isDirectory()) {
            if (ae.getName().contains(fileType)) {
                String temp=ae.getName();
                String fileTime = getTimeFromFileName(ae.getName());
                int time = Integer.parseInt(fileTime);
                if (time >= timeFrom && time <= timeTo) {
                    try {
                        int index= ae.getName().lastIndexOf("/");
                        System.out.println("full file name"+temp);
                        FileOutputStream fos = new FileOutputStream(sourceFolder + "\\" + ae.getName().substring(index,ae.getName().length()));
                        int len;
                        while ((len = ais.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }




    private static String getTimeFromFileName(String fileName) {
        String time=null;
        int index1 = fileName.lastIndexOf(".xml");
        if (index1 != -1) {

            String name= fileName.substring(0, index1);
            String[] split=name.split("_") ;
            for(int i=0;i< split.length;i++) {
                if (split[i].length() == 14) {
                    time = split[i].substring(8, 12);
                }
            }
        }
        return time;

    }
    public static void validateAndCreateDir(String dir) {
        File input = new File(dir);
        if(!input.exists()) input.mkdir();
        else if (!input.isDirectory()) throw new IllegalArgumentException("[" + dir + "] is not directory!");
    }

    /**
     * Throws an IllegalArgumentException if any of the given filePaths are existing files, or are within any other path mentioned in the same list.
     * @param filePaths
     */
    public static void validateDirectories(String... filePaths) {
        // create Paths from string paths - to fix slash, whitespace, etc. inconsistencies.
        Path[] paths = new Path[filePaths.length];
        for (int i = 0; i < filePaths.length; ++i) {
            Path path = Paths.get(filePaths[i]);
            if (path.toFile().exists() && !path.toFile().isDirectory())
                throw new IllegalArgumentException("[" + filePaths[i] + "] is not a directory!");
            paths[i] = path;
        }

        // check that none of the directories exist in any other
        for (int i = 0; i < paths.length; ++i) {
            for (int j = 0; j < paths.length; ++j) {
                if (i == j) continue;
                else if (paths[i].startsWith(paths[j]))
                    throw new IllegalArgumentException("[" + filePaths[i] + "] can not be in [" + filePaths[j] + "]!");
            }
        }
    }

    public static void makeDirectoriesInPath(String directoryPath) {
        File file=new File(directoryPath);
        if(file.exists()) {
            deleteFiles(directoryPath);
            file.mkdir();
        }
        else{
            file.mkdir();
        }
    }

    public static void delete(String dir) {
        new File(dir).delete();
    }

    public static void deleteFiles(String dir) {
        File directory = new File(dir);
        File[] files = directory.listFiles();
        for(File f : files) {
            f.delete();
        }
    }

    public static void deleteDirectory(String dir) {
        File directory = new File(dir);
        if(directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (File f : files) {
                f.delete();
            }
        }
    }

    public static String moveFile(String srcFilePath, String destFolder) throws Exception {
        File afile = new File(srcFilePath);
        return moveFile(afile, destFolder);
    }

    public static String moveFile(File srcFile, String destFolder) throws Exception {
        Path from = srcFile.toPath();
        Path to = new File(destFolder).toPath().resolve(srcFile.getName());
        Path path = Files.move(from, to, REPLACE_EXISTING);
        if (path != null) {
            return path.toString();
        }
        return null;
    }

    public static boolean isFileAvailable(String filePath, boolean isBlocking) throws Exception {
        final long stepMs = 1000;
        final long maxBlock = 5 * 60 * 1000;
        long currentWait = 0;
        /*File file = null;
        long oldSize = 0L;
        long newSize = 1L;

        try{
            file = new File(fileName);
            if(!isBlocking){
                return file.exists();
            }
            else
            {
                boolean rv = file.exists();
                while(!rv){
                    Thread.sleep(stepMs);
                    currentWait += stepMs;
                    rv = file.exists();
                    if(currentWait > maxBlock){
                          throw new Exception("Could not detect the file. Check for slow file transfer of " + fileName);
                    }
                    continue;
                }
                //System.out.println("File exist detected");
                while(newSize > oldSize){
                    oldSize = file.length();
                    Thread.sleep(stepMs);
                    newSize = file.length();
                    //System.out.println(String.format("%d-%d", oldSize, newSize));
                }
                //System.out.println("File available");
                return true;
            }
        }
        catch(Exception ex){

        }
        return false;   */


        RandomAccessFile stream = null;
        boolean isCopying = true;
        while (isBlocking) {
            try {
                logger.entry();
                File file = new File(filePath);
                stream = new RandomAccessFile(file, "rw");
                isCopying = false;
                if (stream != null) {
                    stream.close();
                    stream = null;
                }

            } catch (Exception ex) {
                //System.out.println("File not found or is in copy State. ");
                Thread.sleep(stepMs);
                currentWait += stepMs;
                logger.catching(ex);
                //ex.printStackTrace();
            }
            if (isCopying == false) {
                logger.exit();
                break;
            }
            if (currentWait > maxBlock) {
                logger.throwing(new Exception("Could not detect the file. Check for slow file transfer of " + filePath));
                throw new Exception("Could not detect the file. Check for slow file transfer of " + filePath);
            }
        }
        //System.out.println("copy completed ::");
        logger.exit();
        return !isCopying;
    }

}
