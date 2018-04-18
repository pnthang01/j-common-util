package com.etybeno.common.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by thangpham on 12/04/2018.
 */
public class DateTimeUtilJ8 {

    private static ConcurrentMap<String, DateTimeFormatter> dateFmtMap;

    public static final String YYYYMMDD_DASH = "yyyy-MM-dd";
    public static final String YYYYMMDDHH_DASH = "yyyy-MM-dd-HH";
    public static final String DDMMYYYY_DASH = "dd-MM-yyyy";
    public static final String HHDDMMYYYY_DASH = "HH-dd-MM-yyyy";

    public static final String YYYYMMDD_SPLASH = "yyyy/MM/dd";
    public static final String YYYYMMDDHH_SPLASH = "yyyy/MM/dd/HH";
    public static final String DDMMYYYY_SPLASH = "dd/MM/yyyy";
    public static final String HHDDMMYYYY_SPLASH = "HH/dd/MM/yyyy";

    static {
        synchronized (DateTimeUtil.class) {
            dateFmtMap = new ConcurrentHashMap<>();
            dateFmtMap.put(YYYYMMDD_DASH, DateTimeFormatter.ofPattern(YYYYMMDD_DASH));
            dateFmtMap.put(YYYYMMDDHH_DASH, DateTimeFormatter.ofPattern(YYYYMMDDHH_DASH));
            dateFmtMap.put(DDMMYYYY_DASH, DateTimeFormatter.ofPattern(DDMMYYYY_DASH));
            dateFmtMap.put(HHDDMMYYYY_DASH, DateTimeFormatter.ofPattern(HHDDMMYYYY_DASH));
            //
            dateFmtMap.put(YYYYMMDD_SPLASH, DateTimeFormatter.ofPattern(YYYYMMDD_SPLASH));
            dateFmtMap.put(YYYYMMDDHH_SPLASH, DateTimeFormatter.ofPattern(YYYYMMDDHH_SPLASH));
            dateFmtMap.put(DDMMYYYY_SPLASH, DateTimeFormatter.ofPattern(DDMMYYYY_SPLASH));
            dateFmtMap.put(HHDDMMYYYY_SPLASH, DateTimeFormatter.ofPattern(HHDDMMYYYY_SPLASH));
        }
    }

    private static DateTimeFormatter getDateFormat(String fmt) {
        DateTimeFormatter fdf = dateFmtMap.get(fmt);
        if (null == fdf) dateFmtMap.put(fmt, fdf);
        return fdf;
    }
//
//    public static Date parseDate(String dateStr, String fmt) throws ParseException {
//        getDateFormat(fmt).parse(dateStr).
//        return getDateFormat(fmt).parse(dateStr);
//    }
//
//    public static Date parseDate(String dateStr, String fmt, TimeZone fromTimeZone) throws ParseException {
//        return getDateFormat(fmt, fromTimeZone).parse(dateStr);
//    }

//    public static String formatDate(Date dateTime, String fmt) {
//        return formatDate(dateTime.getTime(), fmt, null);
//    }
//
//    public static String formatDate(Calendar cal, String fmt) {
//        return formatDate(cal.getTimeInMillis(), fmt, null);
//    }
//
//    public static String formatDate(long timeInMilis, String fmt) {
//        return formatDate(timeInMilis, fmt, null);
//    }
//
//    public static String formatDate(Calendar cal, String fmt, TimeZone tz) {
//        return formatDate(cal.getTimeInMillis(), fmt, tz);
//    }
//
//    public static String formatDate(Date dateTime, String fmt, TimeZone tz) {
//        return formatDate(dateTime.getTime(), fmt, tz);
//    }

//    public static String formatDate(long timeInMilis, String fmt, TimeZone tz) {
//        return getDateFormat(fmt, tz).format(timeInMilis);
//    }

}
