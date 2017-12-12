/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;

import com.etybeno.druid.api.filter.*;

/**
 *
 * @author thangpham
 */
public class FilterBuilders {

    public static SelectorFilter selectorFilter() {
        return new SelectorFilter();
    }

    public static AndFilter andFilter() {
        return new AndFilter();
    }

    public static OrFilter orFilter() {
        return new OrFilter();
    }

    public static InFilter inFilter() {
        return new InFilter();
    }

    public static NotFilter notFilter() {
        return new NotFilter();
    }

    public static RegExpFilter regularExpressionFilter() {
        return new RegExpFilter();
    }
    
    public static SearchFilter searchFilter() {
        return new SearchFilter(); 
    }
    
    public static BoundFilter boundFilter() {
        return new BoundFilter();
    }

    public static JavascriptFilter javascript() {
        return new JavascriptFilter();
    }
    
    public static IntervalFilter interval() {
        return new IntervalFilter();
    }
}
