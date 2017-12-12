/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.query;

import com.etybeno.druid.api.PagingSpec;
import com.etybeno.druid.api.Query;
import com.etybeno.druid.api.QueryContext;
import com.etybeno.druid.api.filter.Filter;
import com.etybeno.druid.api.granularity.IGranularity;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author thangpham
 */
public class Select extends Query<SelectResponse> {

    private String queryType = "select";
    private String dataSource;
    private List<String> intervals;
    private boolean descending;
    private Filter filter;
    private IGranularity granularity;
    private List<String> dimensions;
    private List<String> metrics;
    private PagingSpec pagingSpec;
    private QueryContext context;

    public Select context(QueryContext context) {
        this.context = context;
        return this;
    }

    public Select pagingSpec(PagingSpec pagingSpec) {
        this.pagingSpec = pagingSpec;
        return this;
    }

    public Select filter(Filter filter) {
        this.filter = filter;
        return this;
    }

    public Select descending(boolean descending) {
        this.descending = descending;
        return this;
    }

    public Select intervals(Collection<String> intervals) {
        this.intervals = new ArrayList(intervals);
        return this;
    }

    public Select intervals(String... intervals) {
        if (this.intervals == null) {
            this.intervals = new ArrayList();
        }
        this.intervals.addAll(Arrays.asList(intervals));
        return this;
    }

    public Select dataSource(String dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public Select granularity(IGranularity granularity) {
        this.granularity = granularity;
        return this;
    }

    public Select dimensions(Collection<String> dimensions) {
        this.dimensions = new ArrayList(dimensions);
        return this;
    }

    public Select addDimensions(String... dimensions) {
        if (this.dimensions == null) {
            this.dimensions = new ArrayList();
        }
        this.dimensions.addAll(Arrays.asList(dimensions));
        return this;
    }

    public Select metrics(Collection<String> metrics) {
        this.metrics = new ArrayList(metrics);
        return this;
    }

    public Select addMetrics(String... metrics) {
        if (this.metrics == null) {
            this.metrics = new ArrayList();
        }
        this.metrics.addAll(Arrays.asList(metrics));
        return this;
    }

    @Override
    protected Type getResponseType() {
        return new TypeToken<List<SelectResponse>>() {
        }.getType();
    }
}
