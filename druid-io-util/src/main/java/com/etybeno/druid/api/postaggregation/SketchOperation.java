/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.postaggregation;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thangpham
 */
public class SketchOperation extends PostAggregator {

    private Function func;
    private List<PostAggregator> fields;
    private int size;

    public static enum Function {
        @SerializedName("UNION")
        UNION("UNION"),
        @SerializedName("INTERSECT")
        INTERSECT("INTERSECT"),
        @SerializedName("NOT")
        NOT("NOT");

        private String value;

        private Function(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String toString() {
            return this.value;
        }
    }

    public SketchOperation function(Function func) {
        this.func = func;
        return this;
    }

    public SketchOperation setFields(List<PostAggregator> fields) {
        this.fields = fields;
        return this;
    }

    public SketchOperation addFields(PostAggregator... fields) {
        if (this.fields == null) {
            this.fields = new ArrayList();
        }
        this.fields.addAll(Arrays.asList(fields));
        return this;
    }

    public SketchOperation size(int size) {
        this.size = size;
        return this;
    }
}
