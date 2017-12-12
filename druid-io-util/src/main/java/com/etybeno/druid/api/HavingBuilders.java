/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;


import com.etybeno.druid.api.having.*;

/**
 * @author thangpham
 */
public class HavingBuilders {

    public static AndHaving and() {
        return new AndHaving();
    }

    public static DimSelectorHaving dimSelector() {
        return new DimSelectorHaving();
    }

    public static EqualToHaving equalTo() {
        return new EqualToHaving();
    }

    public static FilterHaving filter() {return new FilterHaving();}

    public static GreaterThanHaving greaterThan() {
        return new GreaterThanHaving();
    }

    public static LessThanHaving lessThan() {
        return new LessThanHaving();
    }

    public static NotHaving not() {return new NotHaving();}

    public static OrHaving or() {
        return new OrHaving();
    }

}
