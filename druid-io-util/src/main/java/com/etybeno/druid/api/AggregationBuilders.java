/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;

import com.etybeno.druid.api.aggregation.*;

/**
 *
 * @author thangpham
 */
public class AggregationBuilders {

    public static Cardinality cardinality() {
        return new Cardinality();
    }

    public static Count count() {
        return new Count();
    }

    public static DoubleMax doubleMax() {
        return new DoubleMax();
    }

    public static DoubleMin doubleMin() {
        return new DoubleMin();
    }

    public static DoubleSum doubleSum() {
        return new DoubleSum();
    }

    public static LongMax longMax() {
        return new LongMax();
    }

    public static LongMin longMin() {
        return new LongMin();
    }

    public static LongSum longSum() {
        return new LongSum();
    }

    public static Filtered filtered() {
        return new Filtered();
    }

    public static HyperUnique hyperUnique() {
        return new HyperUnique();
    }

    public static JavaScript javascript() {
        return new JavaScript();
    }

    public static ThetaSketch thetaSketch() {
        return new ThetaSketch();
    }
}
