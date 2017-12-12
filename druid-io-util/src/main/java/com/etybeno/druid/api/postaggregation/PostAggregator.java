/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.postaggregation;

import com.etybeno.druid.api.Aggregation;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author thangpham
 */
public abstract class PostAggregator implements Aggregation{

    public enum Type {
        @SerializedName("arithmetic")
        ARITHMETIC("arithmetic"),
        @SerializedName("fieldAccess")
        FIELD_ACCESSOR("fieldAccess"),
        @SerializedName("constant")
        CONSTANT("constant"),
        @SerializedName("javascript")
        JAVASCRIPT("javascript"),
        @SerializedName("hyperUniqueCardinality")
        HYPERUNIQUE_CARDINALITY("hyperUniqueCardinality"),
        @SerializedName("thetaSketchEstimate")
        THETA_SKETCH_ESTIMATE("thetaSketchEstimate"),
        @SerializedName("thetaSketchSetOp")
        THETA_SKETCH_OPERATION("thetaSketchSetOp");

        private String value;

        private Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String toString() {
            return this.value;
        }
    }

    // attributes
    protected Type type;
    protected String name;
    
}
