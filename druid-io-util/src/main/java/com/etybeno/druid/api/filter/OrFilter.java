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
public class OrFilter extends Filter {

    private List<Filter> fields;

    public OrFilter() {
        this.type = "or";
    }

    public OrFilter setFilters(List<Filter> filters) {
        fields = filters;
        return this;
    }

    public OrFilter addFilters(Filter... filters) {
        if (null == fields) {
            fields = new ArrayList();
        }
        fields.addAll(Arrays.asList(filters));
        return this;
    }
}
