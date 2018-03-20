package com.teoco.rnto.util;

/**
 * Created by IntelliJ IDEA.
 * User: guptaam
 * Date: 11/21/13
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Technology {
    CDMA_EVDO(1),
    GSM_UMTS(2),
    LTE(3),
    UNKNOWN(4);

    public final int value;

    Technology(int value) {
        this.value = value;
    }

    public final static Technology getTechnologyByValue(int value) {
        for (Technology technology : values()) {
            if (technology.value == value)
                return technology;
        }
        return Technology.UNKNOWN;
    }
}
