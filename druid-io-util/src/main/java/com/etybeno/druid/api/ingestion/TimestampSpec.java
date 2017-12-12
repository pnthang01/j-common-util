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
public class TimestampSpec {
    private String column;
    private String format;
    
    public TimestampSpec column(String column) {
        this.column = column;
        return this;
    }
    
    public TimestampSpec format(String format) {
        this.format = format;
        return this;
    }
}
