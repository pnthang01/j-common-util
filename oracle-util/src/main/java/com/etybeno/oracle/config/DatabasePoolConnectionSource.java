package com.etybeno.oracle.config;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by thangpham on 29/12/2017.
 */
public class DatabasePoolConnectionSource {

    private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String SQL_SERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    private OracleConnectionPoolDataSource ocpds = null;
    private String username;
    private String password;
    private String database;
    private String host;
    private int port;
    private String dbdriverclasspath;
    private String minLimit = "2";
    private String maxLimit = "4";
    private String inactiveTimeout = "300";
    private String abandoneTimeout = "900";

    public OracleConnectionPoolDataSource getOcpds() {
        return ocpds;
    }

    public void setOcpds(OracleConnectionPoolDataSource ocpds) {
        this.ocpds = ocpds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbdriverclasspath() {
        return dbdriverclasspath;
    }

    public void setDbdriverclasspath(String dbdriverclasspath) {
        this.dbdriverclasspath = dbdriverclasspath;
    }

    public String getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(String minLimit) {
        this.minLimit = minLimit;
    }

    public String getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(String maxLimit) {
        this.maxLimit = maxLimit;
    }

    public String getInactiveTimeout() {
        return inactiveTimeout;
    }

    public void setInactiveTimeout(String inactiveTimeout) {
        this.inactiveTimeout = inactiveTimeout;
    }

    public String getAbandoneTimeout() {
        return abandoneTimeout;
    }

    public void setAbandoneTimeout(String abandoneTimeout) {
        this.abandoneTimeout = abandoneTimeout;
    }

    public void init() throws SQLException {
        ocpds = new OracleConnectionPoolDataSource();
        Properties props = new Properties();
        props.setProperty("oracle.net.CONNECT_TIMEOUT", "45000");
        props.setProperty("oracle.jdbc.RetainV9LongBindBehavior", "true");
        props.setProperty("MinLimit", minLimit);
        props.setProperty("MaxLimit", maxLimit);
        props.setProperty("InactivityTimeout", inactiveTimeout);
        props.setProperty("AbandonedConnectionTimeout", abandoneTimeout);
        String connectionUrl = getConnectionUrl(dbdriverclasspath, host, port, database);
        ocpds.setDriverType(dbdriverclasspath);
        ocpds.setURL(connectionUrl);
        ocpds.setUser(username);
        ocpds.setPassword(password);
        ocpds.setConnectionProperties(props);
    }

    public Connection getConnection() throws SQLException {
        return ocpds.getConnection();
    }

    public String getConnectionUrl(String driver, String host, int port, String database) {
        StringBuilder s = new StringBuilder();
        if (MYSQL_DRIVER.equals(driver)) {
            s.append("jdbc:mysql://");
            s.append(host).append(":").append(port).append("/");
            s.append(database).append("?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&noAccessToProcedureBodies=true");
        } else if (SQL_SERVER_DRIVER.equals(driver)) {
            s.append("jdbc:sqlserver://");
            s.append(host).append(":").append(port);
            s.append(";databaseName=").append(database);
        } else if (ORACLE_DRIVER.equals(driver)) {
//      System.setProperty("java.security.egd", "file:///dev/urandom");
            String url = "jdbc:oracle:thin:@(description=(address=(host=" + host
                    + ")(protocol=tcp)(port=" + port + "))(connect_data=(service_name=" + database + ")(server=SHARED)))";
            s.append(url);
        } else
            throw new IllegalArgumentException("Currently, only support JDBC driver for MySQL, MSSQL Server and Oracle!");
        return s.toString();
    }
}
