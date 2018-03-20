package com.teoco.mentor;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by roysha on 6/3/2016.
 */
public class FileModified implements Comparable<FileModified> {
    File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    /**
     * compares file based on last modified time
     * @param o
     * @return
     */
    @Override
    public int compareTo(FileModified o) {
        Long fileModified = o.getFile().lastModified();
        if(this.getFile().lastModified()>fileModified)
            return 1;
        else if(this.getFile().lastModified()<fileModified)
            return -1;
        else return 0;


    }
}
