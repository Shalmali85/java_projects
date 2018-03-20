package com.teoco.rnto.util.enums;

/**
 * Constants for Data Collection Services
 */
public enum  DataCollectionServiceType {
    FILE_MONITOR_SERVICE((byte)0, "File monitoring"),
    DATA_COLLECTION_SERVICE((byte)1, "Nodes allocation based collection");

    public byte getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    private final byte type;
    private final String description;

    DataCollectionServiceType(byte type, String description) {
        this.type = type;
        this.description = description;
    }

    public static DataCollectionServiceType byType(byte type) {
        for (DataCollectionServiceType d : values()) {
            if (d.type == type) return d;
        }
        return null;
    }
}
