package com.teoco.rnto.util;

/**
 * Created by IntelliJ IDEA.
 * User: guptaam
 * Date: 11/21/13
 * Time: 1:08 PM
 * To change this template use File | Settings | File Templates.
 */
public enum AgentProcessingType {
    UNKNOWN(0),
    FILE(1),
    STREAMING(2);

    public final int value;

    AgentProcessingType(int value) {
        this.value = value;
    }

    public final static AgentProcessingType getAgentProcessingTypeByValue(int value) {
        for (AgentProcessingType agentProcessingType : values()) {
            if (agentProcessingType.value == value)
                return agentProcessingType;
        }

        return AgentProcessingType.UNKNOWN;
    }
}
