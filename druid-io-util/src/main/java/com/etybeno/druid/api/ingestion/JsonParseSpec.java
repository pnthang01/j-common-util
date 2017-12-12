/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.ingestion;

/**
 *
 * @author thangpham
 */
public class JsonParseSpec extends ParseSpec {

    private final String type = "json";
    private TimestampSpec timestampSpec;
    private DimensionsSpec dimensionsSpec;
    
    public JsonParseSpec timestampSpec(TimestampSpec timestampSpec) {
        this.timestampSpec = timestampSpec;
        return this;
    }
    
    public JsonParseSpec dimensionSpec(DimensionsSpec dimensionsSpec) {
        this.dimensionsSpec = dimensionsSpec;
        return this;
    }
    
    public TimestampSpec getTimestampSpec() {
        return this.timestampSpec;
    }
    
    public DimensionsSpec getDimensionsSpec() {
        return this.dimensionsSpec;
    }
}
