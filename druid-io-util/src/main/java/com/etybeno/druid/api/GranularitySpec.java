/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;


import com.etybeno.druid.api.granularity.Granularity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thangpham
 */
public class GranularitySpec {

    private Granularity segmentGranularity;
    private Granularity queryGranularity;
    private List<String> intervals;

    public GranularitySpec segmentGranularity(Granularity granularity) {
        this.segmentGranularity = granularity;
        return this;
    }

    public GranularitySpec queryGranularity(Granularity granularity) {
        this.queryGranularity = granularity;
        return this;
    }

    public GranularitySpec intervals(String... interval) {
        this.intervals = Arrays.asList(interval);
        return this;
    }

    public GranularitySpec addIntervals(String... intervals) {
        if (this.intervals == null) {
            this.intervals = new ArrayList();
        }
        this.intervals.addAll(Arrays.asList(intervals));
        return this;
    }

}
