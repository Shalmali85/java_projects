package com.teoco.mongo.rop;



import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by roysha on 2/24/2016.
 */
public class RopPeriod {
    public String yyyyMmdd;
    public String startHhMm;
    public String endHhMm;
    private Calendar startOfDay = Calendar.getInstance( TimeZone.getTimeZone("GMT"));

    public long startInMs = 0;
    public int endInMs;

    public RopPeriod(){}

    public RopPeriod(String yyyyMmdd, String startHhMm, String endHhMm){
        this.yyyyMmdd = yyyyMmdd;
        this.startHhMm = startHhMm;
        this.endHhMm = endHhMm;
    }

    public long getRopStartTimeInMs(){
        if(startInMs == 0) {
            String[] splitStartHhMm = this.startHhMm.split(":");
            int yy,  mm,  dd,  hour,  minute;
            yy= Integer.parseInt(yyyyMmdd.substring(0,4));
            mm= Integer.parseInt(yyyyMmdd.substring(4,6));
            dd= Integer.parseInt(yyyyMmdd.substring(6,8));
            hour= Integer.parseInt(startHhMm.substring(0,2));
            minute= Integer.parseInt(startHhMm.substring(2,4));
            startInMs = getUtcTimeInMs(yy,mm,dd,hour,minute,0,0 );
        }
        return startInMs;
    }

    public boolean equals(Object o){
        if(o == null)                return false;
        if(!(o instanceof RopPeriod)) return false;

        RopPeriod other = (RopPeriod) o;
        if(! this.yyyyMmdd.equals(other.yyyyMmdd)) return false;
        if(! this.startHhMm.equals(other.startHhMm)) return false;
        if(endHhMm != null) {
            if (!this.endHhMm.equals(other.endHhMm)) return false;
        }

        return true;
    }

    public int hashCode(){

        if(endHhMm == null) {//mro
            return (int)yyyyMmdd.hashCode() * startHhMm.hashCode();
        }

        return (int)yyyyMmdd.hashCode() * startHhMm.hashCode() *endHhMm.hashCode();
    }

    public String toString(){
        return startHhMm + " " + endHhMm;
    }

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

}
