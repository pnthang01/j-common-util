/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;

/**
 *
 * @author thangpham
 */
public class QueryContext {

    private Integer timeout;
    private Integer priority;
    private String queryId;
    private Boolean useCache;
    private Boolean populateCache;
    private Boolean bySegment;
    private Boolean finalize;
    private Integer chunkPeriod;
    private Integer minTopNThreshold;
    private Integer maxResults;
    private Integer maxIntermediateRows;
    private Boolean groupByIsSingleThreaded;

    public QueryContext timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public QueryContext priority(int priority) {
        this.priority = priority;
        return this;
    }

    public QueryContext queryId(String queryId) {
        this.queryId = queryId;
        return this;
    }

    public QueryContext useCache(boolean useCache) {
        this.useCache = useCache;
        return this;
    }

    public QueryContext populateCache(boolean populateCache) {
        this.populateCache = populateCache;
        return this;
    }

    public QueryContext bySegment(boolean bySegment) {
        this.bySegment = bySegment;
        return this;
    }

    public QueryContext finalize(boolean finalize) {
        this.finalize = finalize;
        return this;
    }

    public QueryContext chunkPeriod(int chunkPeriod) {
        this.chunkPeriod = chunkPeriod;
        return this;
    }

    public QueryContext minTopNThreshold(int minTopNThreshold) {
        this.minTopNThreshold = minTopNThreshold;
        return this;
    }

    public QueryContext maxResults(int maxResults) {
        this.maxResults = maxResults;
        return this;
    }

    public QueryContext maxIntermediateRows(int maxIntermediateRows) {
        this.maxIntermediateRows = maxIntermediateRows;
        return this;
    }

    public QueryContext groupByIsSingleThreaded(boolean groupByIsSingleThreaded) {
        this.groupByIsSingleThreaded = groupByIsSingleThreaded;
        return this;
    }

}
