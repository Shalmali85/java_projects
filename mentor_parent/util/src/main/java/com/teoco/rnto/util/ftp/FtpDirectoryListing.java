package com.teoco.rnto.util.ftp;

import com.teoco.rnto.util.enums.FTPObjectType;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Encapsulates the directory listing of a directory of a ftp site
 */
public class FtpDirectoryListing {
    private ArrayList<FTPObject> ftpObjects = new ArrayList<FTPObject>();
    private Logger logger = LogManager.getLogger();

    public FtpDirectoryListing(FTPClient ftpClient, String remoteDir) {
        try {
            logger.entry();
            FTPFile[] ftpFiles = ftpClient.listFiles(remoteDir);
            for (FTPFile remoteFile : ftpFiles) {
                if (remoteFile.isDirectory())
                    addToList(remoteFile, remoteDir);
            }
            for (FTPFile remoteFile : ftpFiles) {
                if (remoteFile.isFile())
                    addToList(remoteFile, remoteDir);
            }
        } catch (IOException ex) {
            logger.catching(ex);
            ex.printStackTrace();
            logger.exit();
        }
    }

    private void addToList(FTPFile remoteFile, String remoteDir) {
        FTPObject ftpObject = new FTPObject();
        ftpObject.name = remoteFile.getName();
        ftpObject.sizeKB = remoteFile.getSize() / 1024;
        ftpObject.timestamp = remoteFile.getTimestamp().getTime();
        ftpObject.type = remoteFile.isDirectory() ? FTPObjectType.DIRECTORY : FTPObjectType.FILE;
        ftpObject.path = remoteDir;
        ftpObjects.add(ftpObject);
    }

    public void forEach(FTPDirListingProcedure procedure) {
        for (FTPObject ftpObject : ftpObjects) {
            if (!procedure.execute(ftpObject)) break;
        }
    }

    public static interface FTPDirListingProcedure {
        public boolean execute(FTPObject ftpObject);
    }
}
