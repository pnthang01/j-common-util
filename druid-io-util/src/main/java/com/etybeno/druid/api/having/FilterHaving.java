package com.etybeno.druid.api.having;


import com.etybeno.druid.api.filter.Filter;

/**
 * Created by thangpham on 26/06/2017.
 */
public class FilterHaving extends Having {
    private String type = "filter";
    private Filter filter;

    public FilterHaving filter(Filter filter) {
        this.filter = filter;
        return this;
    }
}
