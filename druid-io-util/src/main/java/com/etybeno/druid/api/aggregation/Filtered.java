/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.aggregation;

import com.etybeno.druid.api.filter.Filter;

/**
 *
 * @author thangpham
 */
public class Filtered extends Aggregator {

    private Filter filter;
    private Aggregator aggregator;

    public Filtered() {
        this.type = Aggregator.Type.FILTERED;
    }

    public Filtered filter(Filter filter) {
        this.filter = filter;
        return this;
    }

    public Filtered aggregator(Aggregator agg) {
        this.aggregator = agg;
        return this;
    }

}
