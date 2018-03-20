package com.teoco.rnto.util.enums;

/**
 * Database mode - allows multiple read/write mechanisms for the same database.
 *
 * Created by Shyam on 03-06-2015.
 */
public enum DatabaseMode {
    JDBC,
    JDBC_BATCH,
    HIBERNATE,
    BULK_LOAD
}
