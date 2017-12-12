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
public class LongMax extends Aggregator {

    private String fieldName;

    public LongMax() {
        this.type = Aggregator.Type.LONG_MAX;
    }

    public LongMax(String name, String fieldName) {
        this.type = Aggregator.Type.LONG_MAX;
        this.fieldName = fieldName;
        this.name = name;
    }

    public LongMax name(String name) {
        this.name = name;
        return this;
    }

    public LongMax fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }
}
