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
public class Constant extends PostAggregator {

    private String value;

    public Constant() {
        this.type = Type.CONSTANT;
    }

    public Constant setName(String name) {
        this.name = name;
        return this;
    }

    public Constant setValue(String value) {
        this.value = value;
        return this;
    }
}
