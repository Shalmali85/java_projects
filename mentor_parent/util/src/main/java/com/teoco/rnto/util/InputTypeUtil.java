package com.teoco.rnto.util;

/**
 * Created by IntelliJ IDEA.
 * User: guptaam
 * Date: 11/26/13
 * Time: 12:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class InputTypeUtil {

    public static AgentProcessingType getInputType(String Value){
        if(Value == null) return AgentProcessingType.UNKNOWN;
        if(Value.compareToIgnoreCase("streaming") == 0) return AgentProcessingType.STREAMING;
        if(Value.compareToIgnoreCase("filesystem") == 0) return AgentProcessingType.FILE;
        return AgentProcessingType.UNKNOWN;
    }
}
