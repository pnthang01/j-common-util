package com.etybeno.common.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.IOException;

/**
 * Created by thangpham on 10/12/2017.
 */
public class ApplicationConfiguration {

    private static ApplicationConfiguration _instance;

    public synchronized static ApplicationConfiguration load() throws ConfigurationException, IOException {
        if (null == _instance) _instance = new ApplicationConfiguration();
        return _instance;
    }

    private Configuration config = null;

    public ApplicationConfiguration() throws ConfigurationException, IOException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.properties()
                        .setFileName(BaseConfiguration.getApplicationConfigFile())
                        .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
        config = builder.getConfiguration();
    }

    public Configuration getConfig() {
        return config;
    }
}
