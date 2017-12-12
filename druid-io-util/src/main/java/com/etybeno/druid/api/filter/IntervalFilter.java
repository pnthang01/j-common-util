/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thangpham
 */
public class IntervalFilter extends DimensionFilter<IntervalFilter> {

    private List<String> intervals;

    public IntervalFilter() {
        this.type = "interval";
    }

    public IntervalFilter addIntervals(String... intervals) {
        if (null == this.intervals) {
            this.intervals = new ArrayList();
        }
        this.intervals.addAll(Arrays.asList(intervals));
        return this;
    }

    public IntervalFilter setIntervals(List<String> intervals) {
        this.intervals = intervals;
        return this;
    }
}
