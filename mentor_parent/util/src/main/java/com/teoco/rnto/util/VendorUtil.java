package com.teoco.rnto.util;


/**
 * Created by IntelliJ IDEA.
 * User: guptaam
 * Date: 11/26/13
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class VendorUtil {

    public static Vendor getVendor(String Value) {
        if(Value == null) return Vendor.UNKNOWN;
        if(Value.compareToIgnoreCase("huawei") == 0) return Vendor.HUAWEI;
        if(Value.compareToIgnoreCase("huawei_tmf") == 0) return Vendor.HUAWEI_TMF;
        else if(Value.compareToIgnoreCase("ericsson") == 0) return Vendor.ERICSSON;
        else if(Value.compareToIgnoreCase("ericsson_ctum") == 0) return Vendor.ERICSSON_CTUM;
        else if(Value.compareToIgnoreCase("ericsson_uetr") == 0) return Vendor.ERICSSON_UETR;
        else if(Value.compareToIgnoreCase("nsn") == 0) return Vendor.NSN;
        else if(Value.compareToIgnoreCase("lucent") == 0) return Vendor.LUCENT;
        else if(Value.compareToIgnoreCase("radcom") == 0) return Vendor.RADCOM;
        else if(Value.compareToIgnoreCase("tektronix") == 0) return Vendor.TEKTRONIX;
        else if(Value.compareToIgnoreCase("genex") == 0) return Vendor.GENEX;
        else if(Value.compareToIgnoreCase("exfo") == 0) return Vendor.EXFO;
        else if(Value.compareToIgnoreCase("cm_mro") == 0) return Vendor.CHINAMOBILE_MRO;
        else if(Value.compareToIgnoreCase("cm_mro_drive_test") == 0) return Vendor.CHINAMOBILE_MRO_DRIVE_TEST;
        else if(Value.compareToIgnoreCase("china_ericsson") == 0) return Vendor.CHINA_ERICSSON;
        return Vendor.UNKNOWN;
    }
}
