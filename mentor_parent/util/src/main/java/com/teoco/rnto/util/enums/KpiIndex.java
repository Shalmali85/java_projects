package com.teoco.rnto.util.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guptaam on 8/29/2014.
 */
public class KpiIndex {
    public static final int KPI_RSRP = 0;
    public static final int KPI_RSRQ = 1;
    public static final int KPI_RS_SINR = 2;
    public static final int OVERLAPPING_DEGREE_1_CELL = 3;
    public static final int OVERLAPPING_DEGREE_2_CELL = 4;
    public static final int OVERLAPPING_DEGREE_1 = 5;
    public static final int OVERLAPPING_DEGREE_2 = 6;
    public static final int NUMBER_OF_MMR = 7;
    public static final int KPI_OVERSHOOTING_COST = 8;
    public static final int DL_THROUGHPUT = 9;
    public static final int UL_THROUGHPUT = 10;
    public static final int RRC_COUNT = 11;
    public static final int RRC_DURATION = 12;
    public static final int RRC_DURATION_WEIGHTED = 13;


    private static short currentIndex = 14;

    public static int getKpiSize() {
        return SIZE_KPIS;
    }

    public static void setKpiSize(int KpiSize) {
        if (KpiSize > SIZE_KPIS)
            KpiIndex.SIZE_KPIS = KpiSize;
    }

    private static int SIZE_KPIS=14;
    private static Map<String,Short> dynamicKpis = new HashMap();


    public static synchronized int getIndex(String key){

        if(!dynamicKpis.containsKey(key)){
            if(currentIndex > SIZE_KPIS){
                return -1;
            }
            dynamicKpis.put(key,currentIndex);
            currentIndex++;
        }
        return dynamicKpis.get(key);
    }

}