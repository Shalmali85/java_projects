package com.teoco.rnto.util.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

/**
 * Created by guptaam on 4/17/2015.
 */
public class FtpManager {
    private Logger logger = LogManager.getLogger(FtpManager.class);
    private FTPClient ftpClient = null;

    /**
     * @param server
     * @param port
     * @param user
     * @param pass
     * @throws Exception
     */
    public FtpManager(String server, int port, String user, String pass) throws Exception {
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            int reply = ftpClient.getReplyCode();
            //FTPReply stores a set of constants for FTP reply codes.
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new Exception("Ftp login failure");
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setBufferSize(1024 * 1024 * 10); //10 mb files max
        } catch (SocketException socketException) {
            logger.error(socketException.toString());
            throw new Exception("Ftp login failure");
        } catch (IOException ioException) {
            logger.error(ioException.toString());
            throw new Exception("Ftp login failure");
        }
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    /**
     * @return
     */
    public boolean disconnectClient() {
        try {
            if (ftpClient != null) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException ioException) {
            logger.error(ioException.toString());
            return false;
        }
        return true;
    }

    public String downloadFile(String remoteFilePath, String localDownloadDirectory) throws IOException {
        if (ftpClient == null) throw new IOException("Non-existent FTP connection!");
        if (remoteFilePath == null || remoteFilePath.isEmpty())
            throw new IllegalArgumentException("Remote File Path is empty!");
        if (localDownloadDirectory == null || localDownloadDirectory.isEmpty())
            throw new IllegalArgumentException("Local download directory is empty!");

        int index = remoteFilePath.lastIndexOf('/');
        if (index < 0) index = remoteFilePath.lastIndexOf('\\');
        String fileName = remoteFilePath.substring(index + 1);
        String outputFileName = localDownloadDirectory + "/" + fileName;
        OutputStream downloadOut = null;
        try {
            downloadOut = new FileOutputStream(outputFileName);
            //get the file from the remote system
            boolean rv = ftpClient.retrieveFile(remoteFilePath, downloadOut);
            if (rv == false)
                throw new FileNotFoundException("Remote File does not exist: " + remoteFilePath);
        } finally {
            //close output stream
            if (downloadOut != null) downloadOut.close();
        }
        return outputFileName;
    }

}
