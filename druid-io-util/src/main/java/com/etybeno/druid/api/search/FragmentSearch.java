/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thangpham
 */
public class FragmentSearch extends Search {

    private boolean case_sensitive;
    private List<String> values;

    public FragmentSearch() {
        this.type = "fragment";
    }

    public FragmentSearch addValues(String... values) {
        if (null == this.values) {
            this.values = new ArrayList();
        }
        this.values.addAll(Arrays.asList(values));
        return this;
    }
    
    public FragmentSearch caseSensitive(boolean sensitive) {
        this.case_sensitive = sensitive;
        return this;
    }
}
