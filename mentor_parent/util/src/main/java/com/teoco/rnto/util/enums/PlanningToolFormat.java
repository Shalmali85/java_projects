package com.teoco.rnto.util.enums;



/**
 * Created by IntelliJ IDEA.
 * User: guptaam
 * Date: 6/5/14
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public enum PlanningToolFormat {
    ASSET("ASSET"),      //  Teoco Asset planning tool
    ATOLL("ATOLL"),
    CHINA_MOBILE_EXPORT("CHINA_MOBILE_EXPORT"),
    ATT("ATT"),
    VZW("VZW"),
    HUAWEI_TMF("HUAWEI_TMF"),
    CHINA_ERICSSON("CHINA_ERICSSON"),
    AUSTRALIA_ERICSSON("AUSTRALIA_ERICSSON"),
    COMMON_FORMAT("COMMON_FORMAT"),
    UNKNOWN("UNKNOWN");

    private String value;

    private PlanningToolFormat(String value){
        this.value = value;
    }

    public static PlanningToolFormat findByName(String name){
        for(PlanningToolFormat v : values()){
            if( v.name().equals(name)){
                return v;
            }
        }
        return null;
    }

    public String getValue(){
        return this.value;
    }
}


