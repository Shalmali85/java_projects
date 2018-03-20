package com.teoco.rnto.util;

import com.teoco.rnto.util.enums.PlanningToolFormat;

/**
 * Created by guptaam on 8/28/2014.
 */
public class PlanningToolUtil {
    public static PlanningToolFormat getPlanningTool(String Value) {
        if(Value == null) return PlanningToolFormat.UNKNOWN;
        if(Value.compareToIgnoreCase("asset") == 0) return PlanningToolFormat.ASSET;
        if(Value.compareToIgnoreCase("china_mobile_planning_export") == 0) return PlanningToolFormat.CHINA_MOBILE_EXPORT;
        if(Value.compareToIgnoreCase("att_planning_export") == 0) return PlanningToolFormat.ATT;
        if(Value.compareToIgnoreCase("vzw") == 0) return PlanningToolFormat.VZW;
        if(Value.compareToIgnoreCase("tmf_planning_export") == 0) return PlanningToolFormat.HUAWEI_TMF;
        if(Value.compareToIgnoreCase("china_ericsson_export") == 0) return PlanningToolFormat.CHINA_ERICSSON;
        if(Value.compareToIgnoreCase("australia_ericsson_export") == 0) return PlanningToolFormat.AUSTRALIA_ERICSSON;
        return PlanningToolFormat.UNKNOWN;
    }
}
