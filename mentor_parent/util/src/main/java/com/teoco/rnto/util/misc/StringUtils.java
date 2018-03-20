package com.teoco.rnto.util.misc;

/**
 * Created by guptaam on 4/24/2015.
 */
public class StringUtils {
    public static String removeNonNumericCharacters(String Str) {
        StringBuilder rv = new StringBuilder();
        for (int i = 0; i < Str.length(); i++) {
            if (Str.charAt(i) < '0' || Str.charAt(i) > '9') {
                continue;
            } else {
                rv.append(Str.charAt(i));
            }
        }

        return rv.toString();
    }

    public static boolean isNullOrEmpty(String s) {
        if (s == null) {
            return true;
        }
        else {
            return s.isEmpty();
        }
    }

    public static String stripToLength(int maxAllowedLength, String s) {
        return s.substring(0, Math.min(s.length(), maxAllowedLength));
    }

}
