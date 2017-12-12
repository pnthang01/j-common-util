/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;

import com.etybeno.druid.api.postaggregation.*;

/**
 *
 * @author thangpham
 */
public class PostAggregationBuilders {

    public static Arithmetic arithmetic() {
        return new Arithmetic();
    }

    public static Constant constant() {
        return new Constant();
    }

    public static FieldAccess fieldAccess() {
        return new FieldAccess();
    }

    public static HyperUniqueCardinality hyperUnique() {
        return new HyperUniqueCardinality();
    }

    public static Javascript javascript() {
        return new Javascript();
    }

    public static SketchEstimator sketchEstimator() {
        return new SketchEstimator();
    }

    public static SketchOperation sketchOperation() {
        return new SketchOperation();
    }
}
