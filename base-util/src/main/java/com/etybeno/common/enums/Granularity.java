package com.etybeno.common.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thangpham on 10/04/2018.
 */
public enum Granularity {

    @JsonProperty("ALL")
    @SerializedName("ALL")
    ALL("ALL"),
    @JsonProperty("YEAR")
    @SerializedName("YEAR")
    YEAR("YEAR"),
    @JsonProperty("QUARTER")
    @SerializedName("QUARTER")
    QUARTER("QUARTER"),
    @JsonProperty("MONTH")
    @SerializedName("MONTH")
    MONTH("MONTH"),
    @JsonProperty("WEEK")
    @SerializedName("WEEK")
    WEEK("WEEK"),
    @JsonProperty("DAY")
    @SerializedName("DAY")
    DAY("DAY"),
    @JsonProperty("HOUR")
    @SerializedName("HOUR")
    HOUR("HOUR"),
    @JsonProperty("THIRTY_MINUTE")
    @SerializedName("THIRTY_MINUTE")
    THIRTY_MINUTE("THIRTY_MINUTE"),
    @JsonProperty("FIFTEEN_MINUTE")
    @SerializedName("FIFTEEN_MINUTE")
    FIFTEEN_MINUTE("FIFTEEN_MINUTE"),
    @JsonProperty("MINUTE")
    @SerializedName("MINUTE")
    MINUTE("MINUTE"),
    @JsonProperty("SECOND")
    @SerializedName("SECOND")
    SECOND("SECOND");

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
