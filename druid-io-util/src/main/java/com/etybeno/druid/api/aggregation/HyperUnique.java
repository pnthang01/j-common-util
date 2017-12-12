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
public class HyperUnique extends Aggregator {

    private String fieldName;

    public HyperUnique() {
        this.type = Aggregator.Type.HYPERUNIQUE;
    }

    public HyperUnique(String name, String fieldName) {
        this.type = Aggregator.Type.HYPERUNIQUE;
        this.fieldName = fieldName;
        this.name = name;
    }

    public HyperUnique name(String name) {
        this.name = name;
        return this;
    }
    
    public HyperUnique fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

}
