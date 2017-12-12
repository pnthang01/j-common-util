/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.aggregation;

/**
 *
 * @author thangpham
 */
public class ThetaSketch extends Aggregator {

    private String fieldName;
    private boolean isInputThetaSketch;
    private int size = 16384;

    public ThetaSketch() {
        this.type = Aggregator.Type.DATASKETCH;
    }

    public ThetaSketch fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public ThetaSketch name(String name) {
        this.name = name;
        return this;
    }

    public ThetaSketch inputThetaSketch(boolean isInput) {
        this.isInputThetaSketch = isInput;
        return this;
    }

    public ThetaSketch size(int size) {
        this.size = size;
        return this;
    }
}
