/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.ingestion;

/**
 *
 * @author thangpham
 */
public class DimensionDetail {
    private String type;
    private String name;
    
    public DimensionDetail type(String type){
        this.type = type;
        return this;
    }
    
    public DimensionDetail name(String name) {
        this.name = name;
        return this;
    }
}
