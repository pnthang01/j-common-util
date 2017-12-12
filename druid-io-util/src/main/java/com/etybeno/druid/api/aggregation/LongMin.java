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
public class LongMin extends Aggregator {

    private String fieldName;

    public LongMin() {
        this.type = Aggregator.Type.LONG_MIN;
    }

    public LongMin(String name, String fieldName) {
        this.type = Aggregator.Type.LONG_MIN;
        this.fieldName = fieldName;
        this.name = name;
    }

    public LongMin name(String name) {
        this.name = name;
        return this;
    }

    public LongMin fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }
}
