package com.etybeno.common.config;


import com.etybeno.common.util.StringUtil;

/**
 * Created by thangpham on 20/11/2017.
 */
public class BaseConfiguration {

    private static String baseConfig = "config/";

    private static String REDIS_CONFIG_FILE = "redis-info-configs.properties";
    private static String DRUID_CONFIG_FILE = "druid-uri-configs.properties";
    private static String DATABASE_CONFIG_FILE = "database-info-configs.properties";
    private static String HADOOP_CONFIG_FILE = "hadoop-connection-configs.properties";
    private static String SNAPPYDATA_CONFIG_FILE = "snappy-data-uri-configs.properties";
    private static String ELASTICSEARCH_CONFIG_FILE = "elasticsearch-configs.properties";
    private static String KAFKA_PRODUCERS_CONFIG_FILE = "kafka-producers-configs.properties";
    private static String NETTY_SERVER_CONFIG_FILE = "netty-server-configs.properties";
    private static String MONGODB_CONFIG_FILE = "mongodb-configs.properties";
    private static String GOOGLE_DRIVE_AUTHORIZE_JSON = "client_secrets.json";

    public static void setBaseConfig(String targetConfDir) throws IllegalArgumentException {
        if (StringUtil.isNullOrEmpty(targetConfDir)) throw new IllegalArgumentException(
                "The configuration does not have base config. Could not lookup configurations.");
        synchronized (baseConfig) {
            BaseConfiguration.baseConfig = targetConfDir;
        }
    }

    public static String getGoogleDriveAuthorizeJson() {
        return baseConfig + GOOGLE_DRIVE_AUTHORIZE_JSON;
    }

    public static void setGoogleDriveAuthorizeJson(String fileName) {
        GOOGLE_DRIVE_AUTHORIZE_JSON = fileName;
    }

    public static String getMongodbConfigFile() {
        return baseConfig + MONGODB_CONFIG_FILE;
    }

    public static void setMongodbConfigFile(String filename) {
        MONGODB_CONFIG_FILE = filename;
    }

    public static String getNettyServerConfigFile() {
        return baseConfig + NETTY_SERVER_CONFIG_FILE;
    }

    public static void setNettyServerConfigFile(String fileName) {
        NETTY_SERVER_CONFIG_FILE = fileName;
    }

    public static String getKafkaProducersConfigFile() {
        return baseConfig + KAFKA_PRODUCERS_CONFIG_FILE;
    }

    public static void setKafkaProducersConfigFile(String fileName) {
        KAFKA_PRODUCERS_CONFIG_FILE = fileName;
    }

    public static void setElasticsearchConfigFile(String fileName) {
        ELASTICSEARCH_CONFIG_FILE = fileName;
    }

    public static String getElasticSearchConfigFile() {
        return baseConfig + ELASTICSEARCH_CONFIG_FILE;
    }

    public static void setSnappydataConfigFile(String fileName) {
        SNAPPYDATA_CONFIG_FILE = fileName;
    }

    public static String getSnappyDataConfigFile() {
        return baseConfig + SNAPPYDATA_CONFIG_FILE;
    }

    public static void setDatabaseConfigFile(String fileName) {
        DATABASE_CONFIG_FILE = fileName;
    }

    public static String getDatabaseConfigFile() {
        return baseConfig + DATABASE_CONFIG_FILE;
    }

    public static void setRedisConfigFile(String fileName) {
        REDIS_CONFIG_FILE = fileName;
    }

    public static String getRedisConfigFile() {
        return baseConfig + REDIS_CONFIG_FILE;
    }

    public static void setDruidConfigFile(String fileName) {
        DRUID_CONFIG_FILE = fileName;
    }

    public static String getDruidConfigFile() {
        return baseConfig + DRUID_CONFIG_FILE;
    }

    public static void setHadoopConfigFile(String fileName) {
        HADOOP_CONFIG_FILE = fileName;
    }

    public static String getHadoopConfigFile() {
        return baseConfig + HADOOP_CONFIG_FILE;
    }

}