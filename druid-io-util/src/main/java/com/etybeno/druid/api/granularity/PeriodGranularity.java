/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.granularity;

import java.util.TimeZone;

/**
 *
 * @author thangpham
 */
public class PeriodGranularity implements IGranularity {

    private final String type = "period";
    private String period;
    private String timeZone;
    private String origin;
    
    public PeriodGranularity origin(String origin) {
        this.origin = origin;
        return this;
    }
    
    public PeriodGranularity period(String period) {
        this.period = period;
        return this;
    }

    public PeriodGranularity timeZone(TimeZone timeZone) {
        this.timeZone = timeZone.getID();
        return this;
    }

    public PeriodGranularity timeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }
}
