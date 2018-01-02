package com.etybeno.oracle.config;

import com.etybeno.common.config.BaseConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by thangpham on 29/12/2017.
 */
public class OracleConfiguration implements Serializable {

    private static OracleConfiguration _instance = null;

    public synchronized static OracleConfiguration _load() throws ConfigurationException {
        if (null == _instance) _instance = new OracleConfiguration();
        return _instance;
    }

    private Configuration config = null;
    private ConcurrentMap<String, DatabasePoolConnectionSource> connectionMap;

    public OracleConfiguration() throws ConfigurationException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.properties()
                        .setFileName(BaseConfiguration.getDatabaseConfigFile())
                        .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
        config = builder.getConfiguration();
        connectionMap = new ConcurrentHashMap();
    }

    public Connection getConnection(String sourceName) throws SQLException {
        DatabasePoolConnectionSource dataSource = connectionMap.get(sourceName);
        if (null == dataSource) {
            String sourceProp = "data.database." + sourceName;
            dataSource = new DatabasePoolConnectionSource();
            dataSource.setHost(config.get(String.class, sourceProp + ".host"));
            dataSource.setPort(config.get(Integer.class, sourceProp + ".port"));
            dataSource.setDbdriverclasspath(config.get(String.class, sourceProp + ".dbdriverClasspath"));
            dataSource.setDatabase(config.get(String.class, sourceProp + ".database"));
            dataSource.setUsername(config.get(String.class, sourceProp + ".username"));
            dataSource.setPassword(config.get(String.class, sourceProp + ".password"));
            dataSource.setMinLimit(config.get(String.class, sourceProp + ".MinLimit"));
            dataSource.setMaxLimit(config.get(String.class, sourceProp + ".MaxLimit"));
            dataSource.setInactiveTimeout(config.get(String.class, sourceProp + ".InactivityTimeout"));
            dataSource.setAbandoneTimeout(config.get(String.class, sourceProp + ".AbandonedConnectionTimeout"));
            dataSource.init();
            connectionMap.put(sourceName, dataSource);
        }
        return dataSource.getConnection();
    }
}
