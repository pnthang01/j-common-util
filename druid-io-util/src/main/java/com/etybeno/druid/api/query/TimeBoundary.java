/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.query;

import com.etybeno.druid.api.QueryContext;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 *
 * @author thangpham
 */
public class TimeBoundary extends Query<TimeBoundary, TimeBoundaryResponse> {

    private final String queryType = "timeBoundary";
    private String dataSource;
    private Bound bound;
    private QueryContext context;

    public TimeBoundary context(QueryContext context) {
        this.context = context;
        return this;
    }

    public TimeBoundary bound(Bound bound) {
        this.bound = bound;
        return this;
    }

    @Override
    protected Type getResponseType() {
        return new TypeToken<List<TimeBoundaryResponse>>() {}.getType();
    }

    public static enum Bound {
        @SerializedName("maxTime")
        MAXTIME("maxTime"),
        @SerializedName("minTime")
        MINTIME("minTime");

        private final String value;

        /**
         * @param value
         */
        private Bound(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

}
