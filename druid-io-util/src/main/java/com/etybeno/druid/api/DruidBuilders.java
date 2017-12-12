/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;

/**
 * @author thangpham
 */
public class DruidBuilders {

    public static LimitSpec limitSpec() {
        return new LimitSpec();
    }

    public static OrderByColumnSpec orderByColumn() {
        return new OrderByColumnSpec();
    }

    public static PagingSpec pagingSpec() {
        return new PagingSpec();
    }

    public static QueryContext queryContext() {
        return new QueryContext();
    }
}
