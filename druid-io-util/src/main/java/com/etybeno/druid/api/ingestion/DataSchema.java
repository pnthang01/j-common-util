/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.ingestion;

import com.etybeno.druid.api.GranularitySpec;
import com.etybeno.druid.api.aggregation.Aggregator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author thangpham
 */
public class DataSchema {

    private String dataSource;
    private Parser parser;
    private List<Aggregator> metricsSpec;
    private GranularitySpec granularitySpec;

    public DataSchema dataSource(String dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public DataSchema parser(Parser parser) {
        this.parser = parser;
        return this;
    }

    public Parser getParser() {
        return this.parser;
    }

    public DataSchema metricsSpec(Collection<Aggregator> aggs) {
        this.metricsSpec = new ArrayList(aggs);
        return this;
    }

    public DataSchema addMetricsSpec(Aggregator... aggs) {
        if (this.metricsSpec == null) {
            this.metricsSpec = new ArrayList();
        }
        this.metricsSpec.addAll(Arrays.asList(aggs));
        return this;
    }

}
