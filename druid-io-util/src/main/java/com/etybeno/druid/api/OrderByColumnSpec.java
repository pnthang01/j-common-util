/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;

import com.google.gson.annotations.SerializedName;

/**
 * @author thangpham
 */
public class OrderByColumnSpec {

    public static enum Direction {
        @SerializedName("ascending")
        ASCENDING("ascending"),
        @SerializedName("descending")
        DESCENDING("descending");

        private final String value;

        /**
         * @param value
         */
        private Direction(final String value) {
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

    public static enum Order {
        @SerializedName("lexicographic")
        LEXICOGRAPHIC("lexicographic"),
        @SerializedName("alphanumeric")
        ALPHANUMERIC("alphanumeric"),
        @SerializedName("strlen")
        STRLEN("strlen"),
        @SerializedName("numeric")
        NUMERIC("numeric");

        private final String value;

        /**
         * @param value
         */
        private Order(final String value) {
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

    private String dimension;
    private Direction direction;
    private Order dimensionOrder;

    public OrderByColumnSpec dimension(String dimension) {
        this.dimension = dimension;
        return this;
    }

    public OrderByColumnSpec order(Order order) {
        this.dimensionOrder = order;
        return this;
    }

    public OrderByColumnSpec direction(Direction direction) {
        this.direction = direction;
        return this;
    }

}
