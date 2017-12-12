/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author thangpham
 */
public class PagingSpec {

    private Map<String, Integer> pagingIdentifiers;
    private int threshold;
    private boolean fromNext;

    public PagingSpec pagingIdentifiers(String key, int value) {
        if(pagingIdentifiers == null) pagingIdentifiers = new HashMap();
        pagingIdentifiers.put(key, value);
        return this;
    }
    
    public PagingSpec pagingIdentifiers(Map<String, Integer> pagingIdentifiers) {
        this.pagingIdentifiers = pagingIdentifiers;
        return this;
    }

    public PagingSpec threshold(int threshold) {
        this.threshold = threshold;
        return this;
    }

    public PagingSpec fromNext(boolean fromNext) {
        this.fromNext = fromNext;
        return this;
    }

}
