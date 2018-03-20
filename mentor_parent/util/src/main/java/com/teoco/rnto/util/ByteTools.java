package com.teoco.rnto.util;



public class ByteTools {

	public static int bytes2IntDesc(byte[] byteVal,int offset,int length) {
		int result = 0;
		for (int pos = 0; pos < length; pos++) {
			int tmpVal = (byteVal[pos+offset] << (8 * pos));
			switch (pos) {
			case 3:
				tmpVal = tmpVal & 0xFF000000;
				break;
			case 2:
				tmpVal = tmpVal & 0x00FF0000;
				break;
			case 1:
				tmpVal = tmpVal & 0x0000FF00;
				break;
			case 0:
				tmpVal = tmpVal & 0x000000FF;
				break;
			}
			result = result | tmpVal;
		}
		return result;
	}

	public static int bytes2IntAsc(byte[] byteVal,int offset,int length) {
		int result = 0;
		int fillNum = 4 - length;
		for (int pos = 0; pos < length; pos++) {
			int fillPos = pos + fillNum;
			int tmpVal = (byteVal[offset+pos] << (8 * (3 - fillPos)));
			switch (fillPos) {
			case 0:
				tmpVal = tmpVal & 0xFF000000;
				break;
			case 1:
				tmpVal = tmpVal & 0x00FF0000;
				break;
			case 2:
				tmpVal = tmpVal & 0x0000FF00;
				break;
			case 3:
				tmpVal = tmpVal & 0x000000FF;
				break;
			}
			result = result | tmpVal;
		}
		return result;
	}
	

	public static String bytesToHexString(byte[] byteVal,int offset,int length){       
        StringBuilder stringBuilder = new StringBuilder();       
        if (byteVal == null || byteVal.length <= 0) {       
            return null;       
        }       
        for (int pos = 0; pos < length; pos++) {       
            int iVal = byteVal[pos+offset] & 0xFF;       
            String hVal = Integer.toHexString(iVal);       
            if (hVal.length() < 2) {       
                stringBuilder.append(0);       
            }       
            stringBuilder.append(hVal.toUpperCase());       
        }       
        return stringBuilder.toString();       
    }

}
