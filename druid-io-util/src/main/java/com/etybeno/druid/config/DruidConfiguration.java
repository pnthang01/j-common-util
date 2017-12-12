package com.etybeno.druid.config;

import com.etybeno.common.config.BaseConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by thangpham on 12/12/2017.
 */
public class DruidConfiguration {

    private static DruidConfiguration _instance;

    public synchronized static DruidConfiguration load() throws ConfigurationException, IOException {
        if (null == _instance) _instance = new DruidConfiguration();
        return _instance;
    }

    private Configuration config = null;
    private ConcurrentMap<String, DruidClusterInfo> clusters;

    public DruidConfiguration() throws ConfigurationException, IOException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.properties()
                        .setFileName(BaseConfiguration.getDruidConfigFile())
                        .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
        config = builder.getConfiguration();
        clusters = new ConcurrentHashMap<>();
    }

    private DruidClusterInfo getCluster(String clusterName) {
        DruidClusterInfo druidClusterInfo = clusters.get(clusterName);
        if (null == druidClusterInfo) {
            druidClusterInfo = new DruidClusterInfo();
            druidClusterInfo.setDatasourceURI(config.getString("druid.uri." + clusterName + ".datasource"));
            druidClusterInfo.setMetadataURI(config.getString("druid.uri." + clusterName + ".metadata_datasource"));
            druidClusterInfo.setQueryURI(config.getString("druid.uri." + clusterName + ".query"));
            druidClusterInfo.setRuleURI(config.getString("druid.uri." + clusterName + ".rule"));
            druidClusterInfo.setTaskURI(config.getString("druid.uri." + clusterName + ".post_task"));
            clusters.put(clusterName, druidClusterInfo);
        }
        return druidClusterInfo;
    }

    public String getDruidQueryURI(String clusterName) {
        DruidClusterInfo druidClusterInfo = getCluster(clusterName);
        return druidClusterInfo.getQueryURI();
    }

    public String getDruidTaskURI(String clusterName) {
        DruidClusterInfo druidClusterInfo = getCluster(clusterName);
        return druidClusterInfo.getTaskURI();
    }

    public String getDruidMetadataURI(String clusterName) {
        DruidClusterInfo druidClusterInfo = getCluster(clusterName);
        return druidClusterInfo.getMetadataURI();
    }

    public String getDruidDatasourceURI(String clusterName) {
        DruidClusterInfo druidClusterInfo = getCluster(clusterName);
        return druidClusterInfo.getDatasourceURI();
    }

    public String getDruidRuleURI(String clusterName) {
        DruidClusterInfo druidClusterInfo = getCluster(clusterName);
        return druidClusterInfo.getRuleURI();
    }

}
