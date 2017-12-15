package com.etybeno.common.model;

import com.etybeno.common.util.DateTimeUtil;
import com.etybeno.common.util.StringUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by thangpham on 12/12/2017.
 */
public class IntervalModel {

    private transient Date fromDateTime;
    private String fromDate;
    private transient Date toDateTime;
    private String toDate;
    private String format;
    private int timeType = -2;

    /**
     * The date string will be used as from and to date time, it will not be parsed to date time
     *
     * @param date
     * @throws ParseException
     */
    public IntervalModel(String date) throws ParseException {
        this(date, date, false);
    }

    /**
     * Does not parse dates string to time
     *
     * @param fromDate
     * @param toDate
     * @throws ParseException
     */
    public IntervalModel(String fromDate, String toDate) throws ParseException {
        this(fromDate, toDate, false);
    }

    /**
     * Use default format yyyy-MM-dd-HH to parse for dates when shouldParse is true
     *
     * @param fromDate
     * @param toDate
     * @param shouldParse
     * @throws ParseException
     */
    public IntervalModel(String fromDate, String toDate, boolean shouldParse) throws ParseException {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.format = "yyyy-MM-dd-HH";
        if (shouldParse) {
            this.fromDateTime = DateTimeUtil.parseDate(fromDate, format);
            this.toDateTime = DateTimeUtil.parseDate(toDate, format);
        } else {
            this.fromDateTime = null;
            this.toDateTime = null;
        }
    }

    public IntervalModel(Date fromDateTime, Date toDateTime) {
        this(fromDateTime, toDateTime, "yyyy-MM-dd-HH");
    }

    public IntervalModel(Date fromDateTime, Date toDateTime, String format) {
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.fromDate = DateTimeUtil.formatDate(fromDateTime, format);
        this.toDate = DateTimeUtil.formatDate(toDateTime, format);
        this.format = format;
    }

    public IntervalModel(String fromStr, String toStr, String format) throws ParseException {
        this.fromDateTime = DateTimeUtil.parseDate(fromStr, format);
        this.toDateTime = DateTimeUtil.parseDate(toStr, format);
        this.fromDate = fromStr;
        this.toDate = toStr;
        this.format = format;
    }

    @Override
    public String toString() {
        return "IntervalModel{" + "fromDateTime=" + fromDate + ", toDateTime=" + toDate + '}';
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public Date getFromDateTime() throws ParseException {
        if (null == fromDateTime) {
            synchronized (this) {
                fromDateTime = parseDate(fromDate);
            }
        }
        return fromDateTime;
    }

    public Date getToDateTime() throws ParseException {
        if (null == toDateTime) {
            synchronized (this) {
                toDateTime = parseDate(toDate);
            }
        }
        return toDateTime;
    }

    private Date parseDate(String targetStr) throws ParseException {
        if (StringUtil.isNullOrEmpty(format)) {
            return DateTimeUtil.parseDate(targetStr, DateTimeUtil.YYYYMMDDHH_DASH);
        } else {
            return DateTimeUtil.parseDate(targetStr, format);
        }
    }

    public String getFormat() {
        return format;
    }

    public int getTimeType() {
        if(timeType == -2) {
            if(format.equals(DateTimeUtil.YYYYMMDD_DASH)) timeType = Calendar.DATE;
            else if(format.equals(DateTimeUtil.YYYYMMDDHH_DASH)) timeType = Calendar.HOUR_OF_DAY;
            else timeType = -1;
        }
        return timeType;
    }


}
