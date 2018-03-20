package com.teoco.rnto.util;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by roysha on 2/9/2016.
 */
public  class ProcessFileRun  implements Runnable{
    File file;
    String destinationFolder;
    int timeTo;
    int timeFrom;
    String fileType;

    @Override
    public void run() {
       processZippedFile();

    }

    ProcessFileRun(File file,String destinationFolder,  int timeFrom, String fileType,int timeTo){
        this.file=file;
        this.destinationFolder=destinationFolder;
        this.timeFrom=timeFrom;
        this.fileType=fileType;
        this.timeTo=timeTo;
    }


    private void processZippedFile()
    {

        try
        {
            if (file.getName().endsWith(".zip")) {
                //get the zip file content
                ZipArchiveInputStream zis = new ZipArchiveInputStream(new FileInputStream(file.getAbsolutePath()));
                //get the zipped file list entry
                ZipArchiveEntry ze;
                while ((ze = zis.getNextZipEntry()) != null) {
                    FileOperation.retriveSpecificFilesFromCompressedFolder(ze, zis, destinationFolder, timeFrom, timeTo,fileType,file);
                }
                zis.close();
            } else if (file.getName().endsWith(".tar")) {
                     /* Read TAR File into TarArchiveInputStream */
                TarArchiveInputStream tarFile = new TarArchiveInputStream(new FileInputStream(new File(file.getAbsolutePath())));
                TarArchiveEntry entry ;
                while ((entry = tarFile.getNextTarEntry()) != null) {
                    FileOperation.retriveSpecificFilesFromCompressedFolder(entry, tarFile, destinationFolder, timeFrom,timeTo, fileType,file);
                }
                tarFile.close();

            } else {
                throw new Exception("The format of the file is not supported");
            }

        }catch(Exception e){
            System.out.println("error: \n\t" + e.getMessage());
        }
    }
}
