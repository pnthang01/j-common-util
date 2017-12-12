/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.query;

import com.etybeno.druid.api.Query;
import com.etybeno.druid.api.QueryContext;
import com.etybeno.druid.api.aggregation.Aggregator;
import com.etybeno.druid.api.filter.Filter;
import com.etybeno.druid.api.granularity.IGranularity;
import com.etybeno.druid.api.postaggregation.PostAggregator;
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
public class TimeSeries extends Query<TimeSeriesResponse> {

    private String queryType = "timeseries";
    private String dataSource;
    private boolean descending;
    private List<String> intervals;
    private IGranularity granularity;
    private Filter filter;
    private List<Aggregator> aggregations;
    private List<PostAggregator> postAggregations;
    private QueryContext context;

    public TimeSeries queryContext(QueryContext context) {
        this.context = context;
        return this;
    }

    public TimeSeries dataSource(String datasource) {
        this.dataSource = datasource;
        return this;
    }

    public TimeSeries filter(Filter filter) {
        this.filter = filter;
        return this;
    }

    public TimeSeries descending(boolean descending) {
        this.descending = descending;
        return this;
    }

    public TimeSeries granularity(IGranularity granularity) {
        this.granularity = granularity;
        return this;
    }

    public TimeSeries addIntervals(String... intervals) {
        if (this.intervals == null) {
            this.intervals = new ArrayList();
        }
        this.intervals.addAll(Arrays.asList(intervals));
        return this;
    }

    public TimeSeries intervals(List<String> intervals) {
        this.intervals = intervals;
        return this;
    }

    public TimeSeries aggregations(Collection<Aggregator> aggs) {
        this.aggregations = new ArrayList(aggs);
        return this;
    }

    public TimeSeries addAggregations(Aggregator... aggs) {
        if (this.aggregations == null) {
            this.aggregations = new ArrayList();
        }
        this.aggregations.addAll(Arrays.asList(aggs));
        return this;
    }

    public TimeSeries addPostAggregations(PostAggregator... postAggs) {
        if (this.postAggregations == null) {
            this.postAggregations = new ArrayList();
        }
        this.postAggregations.addAll(Arrays.asList(postAggs));
        return this;
    }

    public TimeSeries postAggregations(Collection<PostAggregator> postAggs) {
        this.postAggregations = new ArrayList(postAggs);
        return this;
    }

    @Override
    protected Type getResponseType() {
        return new TypeToken<List<TimeSeriesResponse>>() {}.getType();
    }
}
