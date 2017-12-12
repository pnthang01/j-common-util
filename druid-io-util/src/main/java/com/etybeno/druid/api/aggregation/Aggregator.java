/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.aggregation;

import com.etybeno.druid.api.Aggregation;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author thangpham
 */
public abstract class Aggregator implements Aggregation {

    public enum Type {
        @SerializedName("count")
        COUNT("count"),
        @SerializedName("longSum")
        LONG_SUM("longSum"),
        @SerializedName("doubleSum")
        DOUBLE_SUM("doubleSum"),
        @SerializedName("doubleMin")
        DOUBLE_MIN("doubleMin"),
        @SerializedName("doubleMax")
        DOUBLE_MAX("doubleMax"),
        @SerializedName("longMin")
        LONG_MIN("longMin"),
        @SerializedName("longMax")
        LONG_MAX("longMax"),
        @SerializedName("javascript")
        JAVASCRIPT("javascript"),
        @SerializedName("cardinality")
        CARDINALITY("cardinality"),
        @SerializedName("hyperUnique")
        HYPERUNIQUE("hyperUnique"),
        @SerializedName("filtered")
        FILTERED("filtered"),
        @SerializedName("thetaSketch")
        DATASKETCH("thetaSketch");

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
