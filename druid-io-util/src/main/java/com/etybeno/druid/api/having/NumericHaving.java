/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.having;


/**
 *
 * @author thangpham
 */
public abstract class NumericHaving<T> extends Having {

    protected String aggregation;
    protected double value;
    
    public T aggregation(String aggName) {
        this.aggregation = aggName;
        return (T) this;
    }
    
    public T value(double value) {
        this.value = value;
        return (T) this;
    }
}
