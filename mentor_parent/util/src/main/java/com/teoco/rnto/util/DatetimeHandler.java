package com.teoco.rnto.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: guptaam
 * Date: 2/21/14
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatetimeHandler {
    //time handling for messages-------------------
    private Calendar startOfDay = Calendar.getInstance( TimeZone.getTimeZone("GMT"));
    StringBuilder rv = new  StringBuilder();
    private static String UTC_TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static String TIMESTAMP_PATTERN = "MM/dd/yyyy HH:mm:ss.SSS";
    SimpleDateFormat sd = new SimpleDateFormat(UTC_TIMESTAMP_PATTERN);
    SimpleDateFormat sd2;
    /***
     *
     * @param Pattern
     */
    public void setUtcPattern(String Pattern){
        UTC_TIMESTAMP_PATTERN = Pattern;
        sd = new SimpleDateFormat(UTC_TIMESTAMP_PATTERN);
    }
    /***
     * Set Time zone as returned by TimeZone.getAvailableIDs()
     * @param TimeZoneID
     */
    public void setTimeZone(String TimeZoneID){
        startOfDay = Calendar.getInstance( TimeZone.getTimeZone(TimeZoneID));
    }

    /**
     * Parses a timestamp in UTC format "yyyy-MM-dd'T'HH:mm:ss.SSS" used in the MRO XML to the number of milliseconds since January 1, 1970, 00:00:00 GMT.
     *
     * @param timestamp String in UTC format "yyyy-MM-dd'T'HH:mm:ss.SSS"
     * @return The number of milliseconds since January 1, 1970, 00:00:00 GMT represented by the timestamp string.
     */
    public long parseUtcTimestamp(String timestamp) throws ParseException {

        //todo : make this configurable
        sd.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return sd.parse(timestamp).getTime();
        }catch(ParseException pex){
            System.out.println(pex.toString());
        }
        return 0;
    }

    /***
     * Parse string returned by @link parseUtcTimestamp to Date
     * @param timestamp
     * @return
     * @throws ParseException
     */
    public Date parseUtcTimestampToDate(String timestamp) throws ParseException {

        if(sd2 ==null){
            sd2 = new SimpleDateFormat(TIMESTAMP_PATTERN);
        }
        try {
            return sd2.parse(timestamp);
        }catch(ParseException pex){
            System.out.println(pex.toString());
        }
        return null;
    }

    /***
     * Get current Utc time
     * example 3/4/2015 19:5:16.286
     * @return
     */
    public String getCurrentUtcString(){
        Date currentDate = new Date();
        return convert(currentDate.getTime());
    }

    /***
     *
     * @return
     */
    public Date getCurrentUtcAsDate() throws Exception{
        Date currentDate = new Date();
        String str = getCurrentUtcString();
        return parseUtcTimestampToDate(str);
    }

    /***
     * Retrun utc time in milliseconds
     * @param yy
     * @param mm
     * @param dd
     * @param hour
     * @param minute
     * @param second
     * @param millisec
     * @return
     */
    public long getUtcTimeInMs(int yy, int mm, int dd, int hour, int minute, int second, int millisec) {
        startOfDay.set(Calendar.YEAR, yy);
        startOfDay.set(Calendar.MONTH, mm-1);
        startOfDay.set(Calendar.DAY_OF_MONTH, dd);
        startOfDay.set(Calendar.HOUR_OF_DAY , hour);
        startOfDay.set(Calendar.MINUTE, minute);
        startOfDay.set(Calendar.SECOND, second);
        startOfDay.set(Calendar.MILLISECOND, millisec);
        return startOfDay.getTimeInMillis();
    }

    public String convert(long gmtTimeInMs){
        startOfDay.setTimeInMillis(gmtTimeInMs);
        rv.delete(0, rv.length());
        rv.append(startOfDay.get(Calendar.MONTH)+1);
        rv.append("/");
        rv.append(startOfDay.get(Calendar.DAY_OF_MONTH));
        rv.append("/");
        rv.append(startOfDay.get(Calendar.YEAR));
        rv.append(" ");
        rv.append(startOfDay.get(Calendar.HOUR_OF_DAY));
        rv.append(":");
        rv.append(startOfDay.get(Calendar.MINUTE));
        rv.append(":");
        rv.append(startOfDay.get(Calendar.SECOND));
        rv.append(".");
        rv.append(startOfDay.get(Calendar.MILLISECOND));
        return rv.toString();
    }

    /**
     * Converts to HH:MM:SS
     * @param millis
     * @return
     */
    public String toDurationString(long millis) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    private  String   cTBCDSymbolString = "0123456789*#abc";
    private  char[]   cTBCDSymbols = cTBCDSymbolString.toCharArray();
     /*
	 * This method converts a TBCD string to a character string.
	 */
    public  java.lang.String tbcdToString (byte[] tbcd) {

        int size = (tbcd == null ? 0 : tbcd.length);
        StringBuffer buffer = new StringBuffer(2*size);
        for (int i=0; i<size; ++i) {
            int octet = tbcd[i];
            int n2 = (octet >> 4) & 0xF;
            int n1 = octet & 0xF;

            if (n1 == 15) {
                    throw new NumberFormatException("Illegal filler in octet n=" + i);
            }
            buffer.append(cTBCDSymbols[n1]);

			if (n2 == 15) {
                if (i != size-1)
                    throw new NumberFormatException("Illegal filler in octet n=" + i);
            } else
                buffer.append(cTBCDSymbols[n2]);
        }

        return buffer.toString();
    }

    /**
     * convert time in ms to the day / mnth / date / timestamp as requested
     * @param time
     * @param type
     * @return
     */
    public String convertTimeWithTimeZone(long time, DateTimeType type) {
        Calendar cal = Calendar.getInstance();
//        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(time);
        String day = formatDateTime(cal.get(Calendar.DAY_OF_MONTH));
        String month = formatDateTime((cal.get(Calendar.MONTH) + 1));
        String year = cal.get(Calendar.YEAR)+"" ;
        String date =  year + " " + month + " " + day ;
        String timestamp = date + " " + formatDateTime(cal.get(Calendar.HOUR_OF_DAY)) + ":"
                + formatDateTime(cal.get(Calendar.MINUTE));
        switch(type)    {
            case DAY : return day;
            case MONTH : return month;
            case DATE : return date;
            case TIMESTAMP: return timestamp;
        }
      return "";
    }

    public String formatDateTime(int time)   {
        if(time < 10) return "0"+time;
        return time+"";

    }

    public static void main(String[] args) {
        System.out.println("convertTimeWithTimeZone(1412640000870,DateTimeType.DAY) = " + new DatetimeHandler().convertTimeWithTimeZone(1412640000870L, DateTimeType.DAY));
        /*TimeZone tz = Calendar.getInstance().getTimeZone();
        System.out.println(tz.getDisplayName()); // (i.e. Moscow Standard Time)
        System.out.println(tz.getID());*/
    }

}
