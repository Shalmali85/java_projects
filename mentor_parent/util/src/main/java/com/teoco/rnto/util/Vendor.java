package com.teoco.rnto.util;

/**
 * Created by IntelliJ IDEA.
 * User: guptaam
 * Date: 11/21/13
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Vendor {
    ERICSSON(1),
    ERICSSON_CTUM(2),
    ERICSSON_UETR(3),
    HUAWEI(4),
    NSN(5),
    LUCENT(6),
    RADCOM(7),
    TEKTRONIX(8),
    GENEX(9),
    EXFO(10),
    CHINAMOBILE_MRO(11),
    CHINAMOBILE_MRO_DRIVE_TEST(12),
    HUAWEI_TMF(13),
    CHINA_ERICSSON(14),
    ASSET_3GA(15),
    DRIVE_TEST_JDSU_EXPORT(16),
    UNKNOWN(17);


    public final int value;

    Vendor(int value) {
        this.value = value;
    }

    public final static Vendor getVendorByValue(int value) {
        for (Vendor vendor : values()) {
            if (vendor.value == value)
                return vendor;
        }
        return Vendor.UNKNOWN;
    }
}
