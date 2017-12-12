/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;

/**
 *
 * @author thaonguyen
 */
public class DropRule {

    // [{"type":"dropByInterval","tieredReplicants":{},"interval":"2016-11-18T17:00:00.000Z/2016-11-18T21:00:00.000Z"}]
    private String type = "dropByInterval";
    private String tieredReplicants = "{}";
    private String interval;

    // constructor
    public DropRule() {
        super();
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public DropRule setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * @return the tieredReplicants
     */
    public String getTieredReplicants() {
        return tieredReplicants;
    }

    /**
     * @param tieredReplicants the tieredReplicants to set
     */
    public DropRule setTieredReplicants(String tieredReplicants) {
        this.tieredReplicants = tieredReplicants;
        return this;
    }

    /**
     * @return the interval
     */
    public String getInterval() {
        return interval;
    }

    /**
     * @param interval the interval to set
     */
    public DropRule setInterval(String interval) {
        this.interval = interval;
        return this;
    }

}
