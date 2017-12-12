/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.query;


import com.etybeno.druid.api.Response;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author thaonguyen
 */
public class GroupByResponse implements Response {

    private String version;
    private String timestamp;
    private TreeMap<String, Object> event;

    // constructor 
    public GroupByResponse() {
        super();
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getEvent() {
        return event;
    }

    public void setEvent(TreeMap<String, Object> event) {
        this.event = event;
    }

}
