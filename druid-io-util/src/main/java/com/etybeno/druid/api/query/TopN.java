/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.query;

import com.etybeno.druid.api.QueryContext;
import com.etybeno.druid.api.aggregation.Aggregator;
import com.etybeno.druid.api.filter.Filter;
import com.etybeno.druid.api.granularity.IGranularity;
import com.etybeno.druid.api.postaggregation.PostAggregator;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thangpham
 */
public class TopN extends TimeQuery<TopN, TopNResponse> {

    private final String queryType = "topN";
    private Filter filter;
    private List<Aggregator> aggregations;
    private List<PostAggregator> postAggregations;
    private String dimension;
    private int threshold;
    private String metric;
    private QueryContext context;

    public TopN filter(Filter filter) {
        this.filter = filter;
        return this;
    }

    public TopN queryContext(QueryContext context) {
        this.context = context;
        return this;
    }

    public TopN dimension(String dimension) {
        this.dimension = dimension;
        return this;
    }

    public TopN threshold(int threshold) {
        this.threshold = threshold;
        return this;
    }

    public TopN metric(String metric) {
        this.metric = metric;
        return this;
    }

    public TopN addAggregations(Aggregator... aggs) {
        if (this.aggregations == null) {
            this.aggregations = new ArrayList();
        }
        this.aggregations.addAll(Arrays.asList(aggs));
        return this;
    }

    public TopN aggregations(List<Aggregator> aggs) {
        this.aggregations = aggs;
        return this;
    }

    public TopN addPostAggregations(PostAggregator... postAggs) {
        if (this.postAggregations == null) {
            this.postAggregations = new ArrayList();
        }
        this.postAggregations.addAll(Arrays.asList(postAggs));
        return this;
    }

    public TopN postAggregations(List<PostAggregator> postAggs) {
        this.postAggregations = postAggs;
        return this;
    }

    @Override
    protected Type getResponseType() {
        return new TypeToken<List<TopNResponse>>() {
        }.getType();
    }

}
