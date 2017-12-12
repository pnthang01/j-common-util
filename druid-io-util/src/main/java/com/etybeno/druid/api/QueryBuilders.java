/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;

import com.etybeno.druid.api.query.*;

/**
 *
 * @author thangpham
 */
public class QueryBuilders {

    public static TopN topN() {
        return new TopN();
    }

    public static GroupBy groupBy() {
        return new GroupBy();
    }

    public static TimeSeries timeSeries() {
        return new TimeSeries();
    }
    
    public static Select select() {
        return new Select();
    }
}
