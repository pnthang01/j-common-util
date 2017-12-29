package com.etybeno.common.util;

import com.etybeno.common.model.IntervalModel;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by thangpham on 20/11/2017.
 */
//<Format, <TimeZone, FastDateFormat>>
public class DateTimeUtil {

    //<Format, <TimeZone, FastDateFormat>>
    private static ConcurrentMap<String, ConcurrentMap<String, FastDateFormat>> dateFmtMap;

    public static final String YYYYMMDD_DASH = "yyyy-MM-dd";
    public static final String YYYYMMDDHH_DASH = "yyyy-MM-dd-HH";
    public static final String DDMMYYYY_DASH = "dd-MM-yyyy";
    public static final String HHDDMMYYYY_DASH = "HH-dd-MM-yyyy";

    static {
        synchronized (DateTimeUtil.class) {
            dateFmtMap = new ConcurrentHashMap<>();
            dateFmtMap.put(YYYYMMDD_DASH, new ConcurrentHashMap<>());
            dateFmtMap.get(YYYYMMDD_DASH).put("System", FastDateFormat.getInstance(YYYYMMDD_DASH));
            dateFmtMap.put(YYYYMMDDHH_DASH, new ConcurrentHashMap<>());
            dateFmtMap.get(YYYYMMDDHH_DASH).put("System", FastDateFormat.getInstance(YYYYMMDDHH_DASH));
            dateFmtMap.put(DDMMYYYY_DASH, new ConcurrentHashMap<>());
            dateFmtMap.get(DDMMYYYY_DASH).put("System", FastDateFormat.getInstance(DDMMYYYY_DASH));
            dateFmtMap.put(HHDDMMYYYY_DASH, new ConcurrentHashMap<>());
            dateFmtMap.get(HHDDMMYYYY_DASH).put("System", FastDateFormat.getInstance(HHDDMMYYYY_DASH));
        }
    }

    public static Date addTime(Date date, int amount, int unit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(unit, amount);
        return cal.getTime();
    }

    public static Date addTime(Calendar cal, int numb, int unit) {
        return addTime(cal.getTime(), numb, unit);
    }

    public static Date parseDate(String dateStr, String fmt) throws ParseException {
        return getDateFormat(fmt, null).parse(dateStr);
    }

    public static Date parseDate(String dateStr, String fmt, TimeZone fromTimeZone) throws ParseException {
        return getDateFormat(fmt, fromTimeZone).parse(dateStr);
    }

    public static String formatDate(Date dateTime, String fmt) {
        return formatDate(dateTime.getTime(), fmt, null);
    }

    public static String formatDate(Calendar cal, String fmt) {
        return formatDate(cal.getTimeInMillis(), fmt, null);
    }

    public static String formatDate(long timeInMilis, String fmt) {
        return formatDate(timeInMilis, fmt, null);
    }

    public static String formatDate(Calendar cal, String fmt, TimeZone tz) {
        return formatDate(cal.getTimeInMillis(), fmt, tz);
    }

    public static String formatDate(Date dateTime, String fmt, TimeZone tz) {
        return formatDate(dateTime.getTime(), fmt, tz);
    }

    public static String formatDate(long timeInMilis, String fmt, TimeZone tz) {
        return getDateFormat(fmt, tz).format(timeInMilis);
    }

    private static FastDateFormat getDateFormat(String fmt, TimeZone tz) {
        ConcurrentMap<String, FastDateFormat> fmtMap = dateFmtMap.get("fmt");
        if (null == fmtMap) {
            fmtMap = new ConcurrentHashMap();
            dateFmtMap.put(fmt, fmtMap);
        }
        String tzId;
        if (null == tz) tzId = "System";
        else tzId = tz.getID();
        FastDateFormat fdf = fmtMap.get(tzId);
        if (null == fdf) {
            fdf = tzId.equals("System") ? FastDateFormat.getInstance(fmt) : FastDateFormat.getInstance(fmt, tz);
            fmtMap.put(tzId, fdf);
        }
        return fdf;
    }

    public static Date truncateDateTime(Calendar cal, int at) {
        return truncateDateTime(cal.getTime(), at);
    }

    public static Date truncateDateTime(Date date, int at) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (Calendar.DATE == at) {
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        } else if (Calendar.HOUR == at || Calendar.HOUR_OF_DAY == at) {
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
        return cal.getTime();
    }

    public static Date[] getLastDays(int lastDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(truncateDateTime(cal, Calendar.DATE));
        cal.add(Calendar.DATE, -1);
        Date toDate = cal.getTime();
        cal.add(Calendar.DATE, -lastDays + 1);
        return new Date[]{cal.getTime(), toDate};
    }

    public static Date[] getLastHours(int lastHours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(truncateDateTime(cal, Calendar.HOUR_OF_DAY));
        cal.add(Calendar.HOUR_OF_DAY, -1);
        Date toHour = cal.getTime();
        cal.add(Calendar.HOUR_OF_DAY, -lastHours + 1);
        return new Date[]{cal.getTime(), toHour};
    }

    public static Date stretchDateTime(Calendar cal, int at) {
        return stretchDateTime(cal.getTime(), at);
    }

    public static Date stretchDateTime(Date date, int at) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (Calendar.DATE == at) {
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
        } else if (Calendar.HOUR == at || Calendar.HOUR_OF_DAY == at) {
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
        }
        return cal.getTime();
    }

    public static IntervalModel convertToInterval(String fromTimeStr, String toTimeStr, int lastDays, int lastHours) {
        IntervalModel rs = null;
        if (!StringUtil.isNullOrEmpty(fromTimeStr) && !fromTimeStr.equals("-")
                && !StringUtil.isNullOrEmpty(toTimeStr) && !toTimeStr.equals("-")) {
            try {
                rs = IntervalModel.getInstance(fromTimeStr, toTimeStr, YYYYMMDD_DASH, Calendar.DATE);
            } catch (ParseException ex) {
                try {
                    rs = IntervalModel.getInstance(fromTimeStr, toTimeStr, YYYYMMDDHH_DASH, Calendar.HOUR_OF_DAY);
                } catch (ParseException e) {

                }
            }
        }
        if (null == rs && lastDays > -1) {
            Date[] dates = getLastDays(lastDays);
            rs = IntervalModel.getInstance(dates[0], dates[1], YYYYMMDD_DASH, Calendar.DATE);
        }
        if (null == rs && lastHours > -1) {
            Date[] dates = getLastHours(lastHours);
            rs = IntervalModel.getInstance(dates[0], dates[1], YYYYMMDDHH_DASH, Calendar.HOUR_OF_DAY);
        }
        if (null == rs)
            throw new IllegalArgumentException(String.format("Could not convert to interval model with from_time = %s, " +
                    "to_time = %s, last_days = %d, last_hours = %d", fromTimeStr, toTimeStr, lastDays, lastHours));
        return rs;
    }
}