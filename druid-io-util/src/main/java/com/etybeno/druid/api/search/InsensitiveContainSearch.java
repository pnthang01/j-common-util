/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.search;

/**
 *
 * @author thangpham
 */
public class InsensitiveContainSearch extends Search {

    private String value;
    
    public InsensitiveContainSearch() {
        this.type = "insensitive_contains";
    }
    
    public InsensitiveContainSearch value(String value) {
        this.value = value;
        return this;
    }
}
