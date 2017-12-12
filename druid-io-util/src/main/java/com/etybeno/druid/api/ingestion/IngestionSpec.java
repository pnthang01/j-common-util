/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.ingestion;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author thangpham
 */
public class IngestionSpec {

    private DataSchema dataSchema;
    private Map<String, Object> ioConfig;
    private Map<String, Object> tuningConfig;

    public IngestionSpec setDataSchema(DataSchema dataSchema) {
        this.dataSchema = dataSchema;
        return this;
    }

    public DataSchema getDataSchema() {
        return this.dataSchema;
    }

    public IngestionSpec ioConfig(Map<String, Object> ioConfig) {
        this.ioConfig = ioConfig;
        return this;
    }

    public IngestionSpec addIOConfig(String key, Object value) {
        if (null == ioConfig) {
            this.ioConfig = new HashMap();
        }
        this.ioConfig.put(key, value);
        return this;
    }

    public IngestionSpec tuningConfig(Map<String, Object> tuningConfig) {
        this.tuningConfig = tuningConfig;
        return this;
    }

    public IngestionSpec addTuningConfig(String key, Object value) {
        if (null == tuningConfig) {
            this.tuningConfig = new HashMap();
        }
        this.tuningConfig.put(key, value);
        return this;
    }
}
