/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;


import com.etybeno.druid.api.granularity.*;

/**
 *
 * @author thangpham
 */
public class GranularityBuilders {
    
    public static DurationGranularity duration() {
        return new DurationGranularity();
    }
    
    public static PeriodGranularity period() {
        return new PeriodGranularity();
    }
}
