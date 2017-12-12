/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.aggregation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thangpham
 */
public class Cardinality extends Aggregator {

    private List<String> fieldNames;

    public Cardinality() {
        this.type = Aggregator.Type.CARDINALITY;
    }

    public Cardinality(String name, List<String> fieldNames) {
        this.type = Aggregator.Type.CARDINALITY;
        this.fieldNames = fieldNames;
        this.name = name;
    }

    public Cardinality name(String name) {
        this.name = name;
        return this;
    }

    public Cardinality addFieldNames(String... fieldNames) {
        if (null == this.fieldNames) {
            this.fieldNames = new ArrayList<>();
        }
        this.fieldNames.addAll(Arrays.asList(fieldNames));
        return this;
    }
}
