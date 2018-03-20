package com.teoco.rnto.util;


/**
 * Created by IntelliJ IDEA.
 * User: guptaam
 * Date: 11/26/13
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class TechnologyUtil {

    public static Technology getTechnology(String Value) {
        if(Value == null) return Technology.UNKNOWN;
        if(Value.compareToIgnoreCase("gsm_umts") == 0) return Technology.GSM_UMTS;
        if(Value.compareToIgnoreCase("lte") == 0) return Technology.LTE;
        if(Value.compareToIgnoreCase("cdma_evdo") == 0) return Technology.CDMA_EVDO;
        return Technology.UNKNOWN;
    }
}
