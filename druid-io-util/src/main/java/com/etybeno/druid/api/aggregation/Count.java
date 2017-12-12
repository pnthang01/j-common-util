/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.aggregation;

/**
 *
 * @author thangpham
 */
public class Count extends Aggregator {

    public Count() {
        this.type = Aggregator.Type.COUNT;
    }

    public Count name(String name) {
        this.name = name;
        return this;
    }

}
