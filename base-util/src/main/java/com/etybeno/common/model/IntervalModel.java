package com.etybeno.common.model;

import com.etybeno.common.util.DateTimeUtil;
import com.etybeno.common.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.beans.Transient;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by thangpham on 08/01/2018.
 */
public class IntervalModel implements Serializable{

    public static final int FORWARD = 1, BACKWARD = -1;

    @JsonIgnore
    private transient Date fromDateTime;
    private String fromDate;
    @JsonIgnore
    private transient Date toDateTime;
    private String toDate;
    private String format;
    private int timeType = -2;

    @JsonIgnore
    private transient Calendar currentRoll;
    @JsonIgnore
    private transient Calendar deadline;
    @JsonIgnore
    private transient int rollMode;
    @JsonIgnore
    private transient int rollTimeUnit;
    @JsonIgnore
    private transient boolean includeStart = true;
    @JsonIgnore
    private transient boolean includeEnd = true;

    /**
     * Your custom Interval format and #Calendar.type
     *
     * @param fromDateStr
     * @param toDateStr
     * @param fmt
     * @param timeType
     */
    private IntervalModel(String fromDateStr, String toDateStr, String fmt, int timeType) throws ParseException {
        this(DateTimeUtil.parseDate(fromDateStr, fmt), DateTimeUtil.parseDate(toDateStr, fmt), fmt, timeType);
    }

    private IntervalModel(Date fromDateTime, Date toDateTime, String fmt, int timeType) {
        if (fromDateTime.compareTo(toDateTime) > 0) throw new IllegalArgumentException("From_time is after To_time");
        this.fromDate = DateTimeUtil.formatDate(fromDateTime, fmt);
        this.toDate = DateTimeUtil.formatDate(toDateTime, fmt);
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.format = fmt;
        this.timeType = timeType;
    }

    public static IntervalModel getInstance(Date fromDateTime, Date toDateTime, int timeType) {
        String fmt;
        if (Calendar.DATE == timeType) fmt = DateTimeUtil.YYYYMMDD_DASH;
        else fmt = DateTimeUtil.YYYYMMDDHH_DASH;
        return new IntervalModel(fromDateTime, toDateTime, fmt, timeType);
    }

    public static IntervalModel getInstance(String fromStr, String toStr, int timeType) throws ParseException {
        String fmt;
        if (Calendar.DATE == timeType) fmt = DateTimeUtil.YYYYMMDD_DASH;
        else fmt = DateTimeUtil.YYYYMMDDHH_DASH;
        return new IntervalModel(fromStr, toStr, fmt, timeType);
    }

    public static IntervalModel getInstance(String fromStr, String toStr) throws ParseException {
        return new IntervalModel(fromStr, toStr, "yyyy-MM-dd-HH", Calendar.HOUR_OF_DAY);
    }

    public static IntervalModel getInstance(Date fromDateTime, Date toDateTime) throws ParseException {
        return new IntervalModel(fromDateTime, toDateTime, "yyyy-MM-dd-HH", Calendar.HOUR_OF_DAY);
    }

    public static IntervalModel getInstance(Date fromDateTime, Date toDateTime, String format, int timeType) {
        return new IntervalModel(fromDateTime, toDateTime, format, timeType);
    }

    public static IntervalModel getInstance(String fromStr, String toStr, String format, int timeType) throws ParseException {
        return new IntervalModel(fromStr, toStr, format, timeType);
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

    public int getTimeType() {
        return timeType;
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

    public IntervalModel[] intersection(IntervalModel o) throws ParseException {
        if (this.timeType != o.timeType)
            throw new IllegalArgumentException("Time type between two intervals must be the same");
        if (!this.getFormat().equals(o.format))
            throw new IllegalArgumentException("Format between two intervals must be the same");
        String fmt = this.getFormat();
        int tt = this.timeType;
        int tvt = this.getToDateTime().compareTo(o.getToDateTime());
        int fvf = this.getFromDateTime().compareTo(o.getFromDateTime());
        int tvf = this.getToDateTime().compareTo(o.getFromDateTime());
        int fvt = this.getFromDateTime().compareTo(o.getToDateTime());
        IntervalModel left = null, right = null;
        if (fvf == 0) {
            if (tvt > 0) right = getInstance(o.getToDateTime(), this.getToDateTime(), fmt, tt);
            else if (tvt < 0 && tvf > 0) right = getInstance(this.getToDateTime(), o.getToDateTime());
        } else if (fvf < 0) {
            if (tvf > 0) {
                left = getInstance(this.getFromDateTime(), o.getFromDateTime(), fmt, tt);
                if (tvt > 0) right = getInstance(o.getToDateTime(), this.getToDateTime(), fmt, tt);
                else if (tvt < 0) right = getInstance(this.getToDateTime(), o.getToDateTime(), fmt, tt);
            } else {
                left = this;
                right = o;
            }
        } else if (fvf > 0) {
            if (fvt < 0) {
                left = getInstance(o.getFromDateTime(), this.getFromDateTime(), fmt, tt);
                if (tvt > 0) right = getInstance(o.getToDateTime(), this.getToDateTime(), fmt, tt);
                else if (tvt < 0) right = getInstance(this.getToDateTime(), o.getToDateTime(), fmt, tt);
            } else {
                left = o;
                right = this;
            }
        }
        if (null != left && null != right) return new IntervalModel[]{left, right};
        else if (null != left) return new IntervalModel[]{left};
        else if (null != right) return new IntervalModel[]{right};
        else return null;
    }

    public IntervalModel union(IntervalModel o) throws ParseException {
        if (this.timeType != o.timeType)
            throw new IllegalArgumentException("Time type between two intervals must be the same");
        if (!this.getFormat().equals(o.format))
            throw new IllegalArgumentException("Format between two intervals must be the same");
        String fmt = this.getFormat();
        int tt = this.timeType;
        int tvt = this.getToDateTime().compareTo(o.getToDateTime());
        int tvf = this.getToDateTime().compareTo(o.getFromDateTime());
        int fvf = this.getFromDateTime().compareTo(o.getFromDateTime());
        int fvt = this.getFromDateTime().compareTo(o.getToDateTime());
        IntervalModel union = null;
        if (fvf == 0) {
            if (tvt > 0) union = getInstance(this.getFromDateTime(), o.getToDateTime(), fmt, tt);
            else if (tvt < 0 && tvf > 0) union = this;
        } else if (fvf < 0) {
            if (tvf > 0) {
                if (tvt > 0) union = o;
                else if (tvt < 0) union = getInstance(o.getFromDateTime(), this.getToDateTime(), fmt, tt);
            } else if (tvf == 0) union = getInstance(this.getToDateTime(), this.getToDateTime(), fmt, tt);
        } else if (fvf > 0) {
            if (fvt < 0) {
                if (tvt > 0) union = getInstance(this.getFromDateTime(), o.getToDateTime(), fmt, tt);
                else union = this;
            } else if (fvt == 0) union = getInstance(this.getFromDateTime(), this.getFromDateTime(), fmt, tt);
        }
        return union;
    }

    public IntervalModel setRollTimeUnit(int timeUnit) {
        this.rollTimeUnit = timeUnit;
        return this;
    }

    public IntervalModel setRollMode(int rollUpMode) {
        this.rollMode = rollUpMode;
        return this;
    }

    public IntervalModel setIncludeStart(boolean include) {
        this.includeStart = include;
        return this;
    }

    public IntervalModel setIncludeEnd(boolean include) {
        this.includeEnd = include;
        return this;
    }

    public IntervalModel setRollOptions(int timeUnit, int rollMode, boolean includeStart, boolean includeEnd) {
        this.rollTimeUnit = timeUnit;
        this.rollMode = rollMode;
        this.includeStart = includeStart;
        this.includeEnd = includeEnd;
        return this;
    }

    public void resetRollup() {
        this.rollMode = 0;
        this.rollTimeUnit = 0;
        this.includeStart = true;
        this.includeEnd = true;
        this.currentRoll = null;
        this.deadline = null;
    }

    /**
     * Get next datetime, the default result is forwarded date. Set roll mode for backward mode.
     * @return
     */
    public Date next() {
        Date rs = null;
        initialeRollup();
        //
        int compare = currentRoll.compareTo(deadline) * -1;
        if (compare == rollMode || (includeEnd && compare == 0)) {
            rs = currentRoll.getTime();
            currentRoll.add(rollTimeUnit, rollMode);
        }
        return rs;
    }

    private synchronized void initialeRollup() {
        if(null == currentRoll) {
            currentRoll = Calendar.getInstance();
            deadline = Calendar.getInstance();
            rollMode = rollMode != -1 && rollMode != 1 ? FORWARD : rollMode;
            rollTimeUnit = rollTimeUnit == 0 ? timeType : rollTimeUnit;
            currentRoll.setTime(rollMode == FORWARD ? fromDateTime : toDateTime);
            if (!includeStart) currentRoll.add(rollTimeUnit, rollMode);
            deadline.setTime(rollMode == FORWARD ? toDateTime : fromDateTime);
        }
    }
}
