package com.etybeno.dbcp.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thangpham on 22/05/2018.
 */
public class SqlPoolInfo {

    private String type;
    private String name;
    private Map<String, String> values;

    public SqlPoolInfo() {
    }

    public SqlPoolInfo(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public void addValue(String key, String value) {
        if(null == values) values = new HashMap<>();
        values.put(key, value);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }
}