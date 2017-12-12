/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.filter;

/**
 *
 * @author thangpham
 */
public class SelectorFilter extends DimensionFilter<SelectorFilter> {

    private String value;

    public SelectorFilter() {
        this.type = "selector";
    }

    public SelectorFilter value(String value) {
        this.value = value;
        return this;
    }
}
