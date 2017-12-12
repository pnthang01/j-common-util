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
public class NotFilter extends Filter {

    private Filter field;

    public NotFilter() {
        this.type = "not";
    }
    
    public NotFilter field(Filter filter) {
        this.field = filter;
        return this;
    }
}
