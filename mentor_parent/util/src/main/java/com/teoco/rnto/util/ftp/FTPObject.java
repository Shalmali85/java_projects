package com.teoco.rnto.util.ftp;

import com.teoco.rnto.util.enums.FTPObjectType;

import java.util.Date;

/**
 * Encapsulates an object(file/directory) on a ftp site.
 */
public class FTPObject {
    public String name;
    public long sizeKB;
    public Date timestamp;
    public FTPObjectType type;
    public String path;
}
