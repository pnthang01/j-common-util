/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.filter;

import com.etybeno.druid.api.aggregation.JavaScript;

/**
 *
 * @author thangpham
 */
public class JavascriptFilter extends Filter<JavaScript> {
    
    private String dimension;
    private String function;

    public JavascriptFilter() {
        this.type = "javascript";
    }
    
    public JavascriptFilter dimension(String dimension) {
        this.dimension = dimension;
        return this;
    }
    
    public JavascriptFilter function(String function) {
        this.function = function;
        return this;
    }
}
