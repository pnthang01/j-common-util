/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thangpham
 */
public class LimitSpec {

    private String type = "default";
    private int limit;
    private List<OrderByColumnSpec> columns;

    public LimitSpec limit(int limit) {
        this.limit = limit;
        return this;
    }
    
    public LimitSpec addColumns(OrderByColumnSpec column) {
        if(null == columns) {
            this.columns = new ArrayList();
        }
        this.columns.add(column);
        return this;
    }
}
