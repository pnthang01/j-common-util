/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**s
 *
 * @author thangpham
 */
public class AndFilter extends Filter {

    private List<Filter> fields;

    public AndFilter() {
        this.type = "and";
    }

    public AndFilter setFilters(List<Filter> filters) {
        fields = filters;
        return this;
    }

    public AndFilter addFilters(Filter... filters) {
        if (null == fields) {
            fields = new ArrayList();
        }
        fields.addAll(Arrays.asList(filters));
        return this;
    }
}
