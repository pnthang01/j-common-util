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

    public DruidConfiguration() throws ConfigurationException, IOException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.properties()
                        .setFileName(BaseConfiguration.getDruidConfigFile())
                        .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
        config = builder.getConfiguration();
    }

}
