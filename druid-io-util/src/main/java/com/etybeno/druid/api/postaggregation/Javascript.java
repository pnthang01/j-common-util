/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.postaggregation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thangpham
 */
public class Javascript extends PostAggregator {

    private List<String> fieldNames;
    private String function;

    public Javascript() {
        this.type = Type.JAVASCRIPT;
    }

    public Javascript setName(String name) {
        this.name = name;
        return this;
    }

    public Javascript addFieldNames(String... fieldNames) {
        if (null == this.fieldNames) {
            this.fieldNames = new ArrayList<>();
        }
        this.fieldNames.addAll(Arrays.asList(fieldNames));
        return this;
    }

    public Javascript setFunction(String function) {
        this.function = function;
        return this;
    }

}
