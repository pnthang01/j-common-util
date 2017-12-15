/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.query;

import com.etybeno.druid.api.LimitSpec;
import com.etybeno.druid.api.QueryContext;
import com.etybeno.druid.api.aggregation.Aggregator;
import com.etybeno.druid.api.filter.Filter;
import com.etybeno.druid.api.granularity.IGranularity;
import com.etybeno.druid.api.having.Having;
import com.etybeno.druid.api.postaggregation.PostAggregator;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author thaonguyen
 */
public class GroupBy extends TimeQuery<GroupBy, GroupByResponse> {

    private final String queryType = "groupBy";
    private List<String> dimensions;
    private LimitSpec limitSpec;
    private Having having;
    private Filter filter;
    private List<Aggregator> aggregations;
    private List<PostAggregator> postAggregations;
    private QueryContext context;

    public GroupBy having(Having having) {
        this.having = having;
        return this;
    }

    public GroupBy context(QueryContext context) {
        this.context = context;
        return this;
    }

    public GroupBy limitSpec(LimitSpec limitSpec) {
        this.limitSpec = limitSpec;
        return this;
    }

    public GroupBy filter(Filter filter) {
        this.filter = filter;
        return this;
    }

    public GroupBy dimensions(Collection<String> dimensions) {
        this.dimensions = new ArrayList(dimensions);
        return this;
    }

    public GroupBy addDimensions(String... dimensions) {
        if (this.dimensions == null) {
            this.dimensions = new ArrayList();
        }
        this.dimensions.addAll(Arrays.asList(dimensions));
        return this;
    }

    public GroupBy aggregations(Collection<Aggregator> aggs) {
        this.aggregations = new ArrayList(aggs);
        return this;
    }

    public GroupBy addAggregations(Aggregator... aggs) {
        if (this.aggregations == null) {
            this.aggregations = new ArrayList();
        }
        this.aggregations.addAll(Arrays.asList(aggs));
        return this;
    }

    public GroupBy postAggregations(Collection<PostAggregator> postAggs) {
        this.postAggregations = new ArrayList(postAggs);
        return this;
    }

    public GroupBy addPostAggregations(PostAggregator... postAggs) {
        if (this.postAggregations == null) {
            this.postAggregations = new ArrayList();
        }
        this.postAggregations.addAll(Arrays.asList(postAggs));
        return this;
    }

    @Override
    protected Type getResponseType() {
        return new TypeToken<List<GroupByResponse>>() {
        }.getType();
    }
}
