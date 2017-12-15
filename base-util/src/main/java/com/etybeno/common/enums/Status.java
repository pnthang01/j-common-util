package com.etybeno.common.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by thangpham on 13/12/2017.
 */
public enum Status {

    @JsonProperty("error")
    ERROR("error"),
    @JsonProperty("success")
    SUCCESS("success"),
    @JsonProperty("failed")
    FAILED("failed");

    private final String value;

    private Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.value;
    }
}

