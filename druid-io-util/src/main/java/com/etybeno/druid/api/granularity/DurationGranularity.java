/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.granularity;

/**
 *
 * @author thangpham
 */
public class DurationGranularity implements IGranularity {

    private final String type = "duration";
    private long duration;
    private String origin;

    public DurationGranularity duration(long duration) {
        this.duration = duration;
        return this;
    }

    public DurationGranularity origin(String origin) {
        this.origin = origin;
        return this;
    }
}
