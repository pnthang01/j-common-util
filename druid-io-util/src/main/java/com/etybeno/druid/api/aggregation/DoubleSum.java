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
public class DoubleSum extends Aggregator {

    private String fieldName;

    public DoubleSum() {
        this.type = Type.DOUBLE_SUM;
    }

    public DoubleSum(String name, String fieldName) {
        this.type = Type.DOUBLE_SUM;
        this.fieldName = fieldName;
        this.name = name;
    }

    public DoubleSum name(String name) {
        this.name = name;
        return this;
    }

    public DoubleSum fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

}
