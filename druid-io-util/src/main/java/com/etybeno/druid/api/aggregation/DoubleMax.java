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
public class DoubleMax extends Aggregator {

    private String fieldName;

    public DoubleMax() {
        this.type = Aggregator.Type.DOUBLE_MAX;
    }

    public DoubleMax(String name, String fieldName) {
        this.type = Aggregator.Type.DOUBLE_MAX;
        this.fieldName = fieldName;
        this.name = name;
    }

    public DoubleMax name(String name) {
        this.name = name;
        return this;
    }

    public DoubleMax fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }
}
