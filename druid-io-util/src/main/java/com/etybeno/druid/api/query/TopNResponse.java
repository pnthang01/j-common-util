/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.query;


import com.etybeno.druid.api.Response;

import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author thangpham
 */
public class TopNResponse implements Response {

    private String timestamp;
    private List<TreeMap<String, Object>> result;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<TreeMap<String, Object>> getResult() {
        return result;
    }

    public void setResult(List<TreeMap<String, Object>> result) {
        this.result = result;
    }

}
