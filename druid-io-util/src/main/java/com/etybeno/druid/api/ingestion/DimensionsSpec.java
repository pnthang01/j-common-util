/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.ingestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thangpham
 */
public class DimensionsSpec {
    
    private List<Object> dimensions;
    private List<String> dimensionExclusions = new ArrayList();
    private List<String> spatialDimensions = new ArrayList();
    
    public DimensionsSpec dimensions(ArrayList<Object> dimensions) {
        for(Object dim : dimensions) {
            if(!(dim instanceof String) || !(dim instanceof DimensionDetail)) {
                throw new IllegalArgumentException("The dimension type must be String or DimensionDetail");
            }
        }
        this.dimensions = dimensions;
        return this;
    }
    
    public DimensionsSpec addDimensions(Object... dimension) {
        for(Object dim : dimension) {
            if(!(dim instanceof String) || !(dim instanceof DimensionDetail)) {
                throw new IllegalArgumentException("The dimension type must be String or DimensionDetail");
            }
        }
        if(null == dimensions) {
            this.dimensions = new ArrayList();
        }
        this.dimensions.addAll(Arrays.asList(dimension));
        return this;
    }
}
