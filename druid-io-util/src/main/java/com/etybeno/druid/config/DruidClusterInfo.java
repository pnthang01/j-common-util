package com.etybeno.druid.config;

/**
 * Created by thangpham on 12/12/2017.
 */
public class DruidClusterInfo {

    private String queryURI;
    private String taskURI;
    private String metadataURI;
    private String datasourceURI;
    private String ruleURI;

    public String getQueryURI() {
        return queryURI;
    }

    public void setQueryURI(String queryURI) {
        this.queryURI = queryURI;
    }

    public String getTaskURI() {
        return taskURI;
    }

    public void setTaskURI(String taskURI) {
        this.taskURI = taskURI;
    }

    public String getMetadataURI() {
        return metadataURI;
    }

    public void setMetadataURI(String metadataURI) {
        this.metadataURI = metadataURI;
    }

    public String getDatasourceURI() {
        return datasourceURI;
    }

    public void setDatasourceURI(String datasourceURI) {
        this.datasourceURI = datasourceURI;
    }

    public String getRuleURI() {
        return ruleURI;
    }

    public void setRuleURI(String ruleURI) {
        this.ruleURI = ruleURI;
    }
}
