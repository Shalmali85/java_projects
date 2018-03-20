package com.teoco.rnto.util.enums;

/**
 * Created by Abhishek on 05-12-2014.
 */
public enum DatabaseNames {
    AGENT_DB,
    CLUSTER_DB;

    public static DatabaseNames get(String dbName) {
        if (dbName.equalsIgnoreCase("agent_db")) {
            return AGENT_DB;
        } else if (dbName.equalsIgnoreCase("sigma_cluster_db")) {
            return CLUSTER_DB;
        }
        return null;
    }
}
