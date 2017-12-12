/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thangpham
 */
public class InFilter extends DimensionFilter<InFilter> {

    private List<String> values;

    public InFilter() {
        this.type = "in";
    }

    public InFilter setFilters(List<String> values) {
        this.values = values;
        return this;
    }

    public InFilter addFilters(String... values) {
        if (null == this.values) {
            this.values = new ArrayList();
        }
        this.values.addAll(Arrays.asList(values));
        return this;
    }
}
