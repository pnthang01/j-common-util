/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;

import com.etybeno.common.util.DateTimeUtil;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author thangpham
 */
public class DruidUtil {

    private static final FastDateFormat YYYYMMDDHH_UTC_DRUID = FastDateFormat.getInstance("yyyy-MM-dd'T'HH", TimeZone.getTimeZone("UTC"));

    public static String formatYYYYMMDDHH_Utc_Druid(long timeInMilis) {
        return YYYYMMDDHH_UTC_DRUID.format(timeInMilis);
    }

    public static String getIntervalInUTC(Date fromTime, Date toTime) {
//        DateTimeUtil.formatDate(fromTime, "yyyy-MM-dd'T'HH", TimeZone.getTimeZone("UTC"));
        return formatYYYYMMDDHH_Utc_Druid(fromTime.getTime()) + "/" + formatYYYYMMDDHH_Utc_Druid(toTime.getTime());
    }
}
