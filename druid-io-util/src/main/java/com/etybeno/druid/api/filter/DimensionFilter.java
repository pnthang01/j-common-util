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
public abstract class DimensionFilter<T> extends Filter {

    private String dimension;

    public T dimension(String dimension) {
        this.dimension = dimension;
        return (T) this;
    }
}
