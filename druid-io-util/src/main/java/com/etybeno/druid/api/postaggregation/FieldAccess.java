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
public class FieldAccess extends PostAggregator {

    private String fieldName;

    public FieldAccess() {
        this.type = Type.FIELD_ACCESSOR;
    }

    public FieldAccess name(String name) {
        this.name = name;
        return this;
    }

    public FieldAccess fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }
}
