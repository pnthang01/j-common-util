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
public class DoubleMin extends Aggregator {

    private String fieldName;

    public DoubleMin() {
        this.type = Type.DOUBLE_MIN;
    }

    public DoubleMin(String name, String fieldName) {
        this.type = Type.DOUBLE_MIN;
        this.fieldName = fieldName;
        this.name = name;
    }

    public DoubleMin name(String name) {
        this.name = name;
        return this;
    }

    public DoubleMin fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

}
