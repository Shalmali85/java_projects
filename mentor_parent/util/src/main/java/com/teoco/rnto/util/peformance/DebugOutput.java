package com.teoco.rnto.util.peformance;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by guptaam on 6/17/2015.
 * Helper class to manage debug files
 */
public class DebugOutput {
    static Map<String, PrintWriter> tsvNameToPw = new HashMap<String, PrintWriter>();

    /**
     * Start a file output stream
     * @param loggerName
     * @return
     */
    public static boolean start(String loggerName){
        if(tsvNameToPw.containsKey(loggerName)){
            return false;
        }
        FileOutputStream fileOutputStream;
        try{
            fileOutputStream = new FileOutputStream(new File("c:\\temp", loggerName), true);
        }catch(FileNotFoundException fileNotFoundException){
            return false;
        }
        tsvNameToPw.put( loggerName, new PrintWriter(fileOutputStream));
        return true;
    }

    /**
     * Close a file output stream
     * @param loggerName
     * @return
     */
    public static boolean stop(String loggerName){
        if(!tsvNameToPw.containsKey(loggerName)){
            return false;
        }
        tsvNameToPw.get( loggerName).close();
        tsvNameToPw.remove( loggerName);
        return true;
    }


    /**
     * Write to a file
     * @param loggerName
     * @param line
     * @return
     */
    public static boolean write(String loggerName, String line){
        if(!tsvNameToPw.containsKey(loggerName)){
            return false;
        }
        tsvNameToPw.get( loggerName).write(line);
        return true;
    }
}
