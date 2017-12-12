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
public class Arithmetic extends PostAggregator {

    public enum Fn {
        @SerializedName("+")
        PLUS("+"),
        @SerializedName("-")
        MINUS("-"),
        @SerializedName("*")
        MULTIPLY("*"),
        @SerializedName("/")
        DIVIDE("/"),
        @SerializedName("quotient")
        QUOTIENT("quotient");

        private String value;

        private Fn(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String toString() {
            return this.value;
        }
    }

    private Fn fn;
    private List<PostAggregator> fields;
    private String ordering;

    //numericFirst
    public Arithmetic setOrdering(String ordering) {
        this.ordering = ordering;
        return this;
    }

    public Arithmetic addPostAggregator(PostAggregator... aggs) {
        if (null == fields) {
            fields = new ArrayList<>();
        }
        fields.addAll(Arrays.asList(aggs));
        return this;
    }

    public Arithmetic setFn(Fn fn) {
        this.fn = fn;
        return this;
    }

    public Arithmetic setName(String name) {
        this.name = name;
        return this;
    }

    public Arithmetic() {
        this.type = Type.ARITHMETIC;
    }
}
