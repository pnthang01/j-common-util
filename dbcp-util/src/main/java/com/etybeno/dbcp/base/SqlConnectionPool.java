package com.etybeno.dbcp.base;

import com.etybeno.common.util.StringUtil;
import com.etybeno.dbcp.config.SqlPoolConfiguration;
import com.etybeno.dbcp.config.SqlPoolInfo;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by thangpham on 22/05/2018.
 */
public class SqlConnectionPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlConnectionPool.class);
    private static SqlConnectionPool _instance;

    public synchronized static SqlConnectionPool _load() throws ConfigurationException {
        if (null == _instance) _instance = new SqlConnectionPool();
        return _instance;
    }

    private SqlPoolConfiguration sqlPoolConfiguration;
    private ConcurrentMap<String, BasicDataSource> datasources = new ConcurrentHashMap<>();


    public SqlConnectionPool() throws ConfigurationException {
        sqlPoolConfiguration = SqlPoolConfiguration._load();
    }

    public synchronized BasicDataSource getBasicDataSource(String name) {
        BasicDataSource basicDataSource = datasources.get(name);
        if (null == basicDataSource) {
            SqlPoolInfo poolInfo = sqlPoolConfiguration.getSqlPoolInfo(name);
            Map<String, String> values = poolInfo.getValues();
            basicDataSource = new BasicDataSource();
            datasources.put(name, basicDataSource);
            Method[] declaredMethods = BasicDataSource.class.getDeclaredMethods();
            for (Method method : declaredMethods) {
                try {
                    String valueStr = values.get(method.getName());
                    if (StringUtil.isNullOrEmpty(valueStr)) continue;
                    Class<?> type = method.getParameterTypes()[0];//only 1 parameter
                    Object value;
                    if (type == long.class || type == Long.class) value = StringUtil.safeParseLong(valueStr);
                    else if (type == boolean.class || type == Boolean.class)
                        value = StringUtil.safeParseBoolean(valueStr);
                    else if (type == int.class || type == Integer.class) value = StringUtil.safeParseInt(valueStr);
                    else value = valueStr;
                    System.out.println("Set data to datasource via " + method.getName());
                    method.invoke(basicDataSource, value);
                } catch (Exception ex) {
                    LOGGER.error(String.format("Can not initialize connection pool: %s", name), ex);
                }
            }
        }
        return basicDataSource;
    }

    public Connection getConnection(String name) throws SQLException {
        BasicDataSource basicDataSource = getBasicDataSource(name);
        return basicDataSource.getConnection();
    }

}
