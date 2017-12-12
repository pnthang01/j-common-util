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
public class HyperUniqueCardinality extends PostAggregator {

    private String fieldName;

    public HyperUniqueCardinality() {
        this.type = Type.HYPERUNIQUE_CARDINALITY;
    }

    public HyperUniqueCardinality name(String name) {
        this.name = name;
        return this;
    }

    public HyperUniqueCardinality fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }
}
