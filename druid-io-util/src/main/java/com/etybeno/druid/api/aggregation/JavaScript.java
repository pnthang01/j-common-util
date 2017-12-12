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
public class JavaScript extends Aggregator {

    private List<String> fieldNames;
    private String fnAggregate;
    private String fnCombine;
    private String fnReset;

    public JavaScript() {
        this.type = Aggregator.Type.JAVASCRIPT;
    }

    public JavaScript(String name, List<String> fieldNames) {
        this.type = Aggregator.Type.JAVASCRIPT;
        this.fieldNames = fieldNames;
        this.name = name;
    }

    public JavaScript name(String name) {
        this.name = name;
        return this;
    }

    public JavaScript addFieldNames(String... fieldNames) {
        if (null == this.fieldNames) {
            this.fieldNames = new ArrayList<>();
        }
        this.fieldNames.addAll(Arrays.asList(fieldNames));
        return this;
    }

    public JavaScript fnAggregate(String fnAggregate) {
        this.fnAggregate = fnAggregate;
        return this;
    }

    public JavaScript fnCombine(String fnCombine) {
        this.fnCombine = fnCombine;
        return this;
    }

    public JavaScript fnReset(String fnReset) {
        this.fnReset = fnReset;
        return this;
    }

}
