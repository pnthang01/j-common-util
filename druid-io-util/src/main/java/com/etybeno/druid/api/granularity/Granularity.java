/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.granularity;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author thangpham
 */
public enum Granularity implements IGranularity {
    @SerializedName("all")
    ALL("all"),
    @SerializedName("year")
    YEAR("year"),
    @SerializedName("quarter")
    QUARTER("quarter"),
    @SerializedName("month")
    MONTH("month"),
    @SerializedName("week")
    WEEK("week"),
    @SerializedName("day")
    DAY("day"),
    @SerializedName("hour")
    HOUR("hour"),
    @SerializedName("thirty_minute")
    THIRTY_MINUTE("thirty_minute"),
    @SerializedName("fifteen_minute")
    FIFTEEN_MINUTE("fifteen_minute"),
    @SerializedName("minute")
    MINUTE("minute"),
    @SerializedName("second")
    SECOND("second");

    private final String value;

    /**
     * @param value
     */
    private Granularity(final String value) {
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
