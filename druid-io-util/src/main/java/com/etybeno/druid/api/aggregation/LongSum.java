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
public class LongSum extends Aggregator {

    private String fieldName;

    public LongSum() {
        this.type = Type.LONG_SUM;
    }

    public LongSum(String name, String fieldName) {
        this.type = Type.LONG_SUM;
        this.fieldName = fieldName;
        this.name = name;
    }

    public LongSum name(String name) {
        this.name = name;
        return this;
    }

    public LongSum fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

}
