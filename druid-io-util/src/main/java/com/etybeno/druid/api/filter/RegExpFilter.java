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
public class RegExpFilter extends DimensionFilter<RegExpFilter> {

    private String pattern;

    public RegExpFilter() {
        this.type = "regex";
    }

    public RegExpFilter pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }
}
