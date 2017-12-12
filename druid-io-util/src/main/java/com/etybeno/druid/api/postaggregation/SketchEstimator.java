/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.postaggregation;

/**
 *
 * @author thangpham
 */
public class SketchEstimator extends PostAggregator {

    private PostAggregator field;

    public SketchEstimator() {
        this.type = Type.THETA_SKETCH_ESTIMATE;
    }

    public SketchEstimator name(String name) {
        this.name = name;
        return this;
    }
    
    public SketchEstimator field(PostAggregator field) {
        this.field = field;
        return this;
    }
}
