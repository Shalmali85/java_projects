package com.teoco.rnto.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: guptaam
 * Date: 9/2/14
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileUtils {
    /**
     * Reads data to the buffer, until the buffer is full or there's no more data to read.
     *
     * @param in     InputStream to read from
     * @param buffer Byte array to fill with data
     * @return Number of bytes read.
     */
    public static int readToBuffer(InputStream in, byte[] buffer) throws IOException {
        return readToBuffer(in, buffer, buffer.length);
    }


    /**
     * Reads a number of bytes to the buffer.
     *
     * @param in     InputStream to read from
     * @param buffer Byte array to fill with data
     * @param size   Number of bytes to read. Must not be larger than buffer length.
     * @return Number of bytes read.
     */
    public static int readToBuffer(InputStream in, byte[] buffer, int size) throws IOException {
        assert size <= buffer.length;
        int offset = 0;
        int numRead;
        while (offset < size && (numRead = in.read(buffer, offset, size - offset)) >= 0) {
            offset += numRead;
        }
        return offset;
    }
}
