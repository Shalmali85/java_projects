package com.teoco.rnto.util.enums;

/**
 * Created by guptaam on 2/16/2015.
 */
public enum ProcessType {
    /**
     * Log Processor.
       This is triggered by the Data Collection Service, and processes files from one or more ROP Periods. The status will give the total time taken to process all data moved by the Data Collection Service.
     */
    LOG_PROCESSOR_PRODUCER((byte) 1),
    MBMS_CLUSTER((byte) 2),
    /**
     * Log Processor Per ROP.
       This process signifies the processing of files in a single ROP period. The status describes how many files were processed in how much time and for which ROP period.
     */
    LOG_PROCESSOR_PER_ROP((byte) 3),
    /**
     * Data Collection Service.
       The status will describe how many log files were moved in how much time. This is a scheduled service which triggers the processing of the files.
     */
    DATA_COLLECTION_SERVICE((byte) 4);

    /**
     * Value of PROCESS_ID in PROCESSES table.
     */
    public final byte ID;

    ProcessType(byte id) {
        this.ID = id;
    }
}
