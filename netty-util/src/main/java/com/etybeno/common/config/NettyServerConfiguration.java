package org.iff.common.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.IOException;

/**
 * Created by thangpham on 20/11/2017.
 */
public class NettyServerConfiguration {

    private Configuration config = null;
    private static NettyServerConfiguration _instance;

    public synchronized static NettyServerConfiguration load() throws ConfigurationException, IOException {
        if (null == _instance) _instance = new NettyServerConfiguration();
        return _instance;
    }

    public NettyServerConfiguration() throws ConfigurationException, IOException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.properties()
                        .setFileName(BaseConfiguration.getNettyServerConfigFile())
                        .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
        config = builder.getConfiguration();
    }

    public String getServerHost() {
        return config.getString("service.netty.host");
    }

    public int getServerPort() {
        return config.getInt("service.netty.port");
    }
}
