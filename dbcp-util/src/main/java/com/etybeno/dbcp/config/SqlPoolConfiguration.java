package com.etybeno.dbcp.config;

import com.etybeno.common.config.BaseConfiguration;
import com.etybeno.common.util.StringUtil;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by thangpham on 22/05/2018.
 */
public class SqlPoolConfiguration {

    private static SqlPoolConfiguration _instance;

    public synchronized static SqlPoolConfiguration _load() throws ConfigurationException {
        if(null ==_instance) _instance = new SqlPoolConfiguration();
        return _instance;
    }

    private Configuration configuration;
    private ConcurrentMap<String, SqlPoolInfo> poolInfoMap;

    public SqlPoolConfiguration() throws ConfigurationException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.properties()
                        .setFileName(BaseConfiguration.getDatabaseConfigFile())
                        .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
        configuration = builder.getConfiguration();
        poolInfoMap = new ConcurrentHashMap<>();
    }

    /**
     * Get SqlPoolInfo. The name must be in format [sql_type]:[name], eg: mysql:location
     * @param name
     * @return
     */
    public SqlPoolInfo getSqlPoolInfo(String name) {
        SqlPoolInfo poolInfo = poolInfoMap.get(name);
        if(null == poolInfo) {
            if (StringUtil.isNullOrEmpty(name.trim()))
                throw new IllegalArgumentException("Pool info's name must not be null");
            String[] split = name.trim().split(":");
            if (split.length != 2) throw new IllegalArgumentException("Pool info's name is wrong format");
            //
            String keyPrefix = StringUtil.toString(".", "pool", split[0], split[1]);
            Iterator<String> keys = configuration.getKeys(keyPrefix);
            poolInfo = new SqlPoolInfo(split[0], split[1]);
            String key;
            while(keys.hasNext()) {
                key = keys.next();
                String value = configuration.getString(key);
                poolInfo.addValue("set" + StringUtils.capitalize(key.substring(keyPrefix.length() + 1, key.length())), value);
            }
        }
        return poolInfo;
    }

}
