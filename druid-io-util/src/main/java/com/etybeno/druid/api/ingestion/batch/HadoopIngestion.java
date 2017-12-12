/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.ingestion.batch;

import com.etybeno.druid.api.ingestion.IngestionSpec;

/**
 *
 * @author thangpham
 */
public class HadoopIngestion {
    
    private final String type = "index_hadoop";
    private IngestionSpec ingestionSpec;
    
    public HadoopIngestion ingestionSpec(IngestionSpec ingestionSpec) {
        this.ingestionSpec = ingestionSpec;
        return this;
    }
    
    public IngestionSpec getSpec() {
        return this.ingestionSpec;
    }
}
