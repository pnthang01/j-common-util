package com.etybeno.mongodb.config;

import com.etybeno.common.config.BaseConfiguration;
import com.etybeno.common.util.StringUtil;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by thangpham on 27/11/2017.
 */
public class MongoDBConfiguration {

    private static MongoDBConfiguration _instance;

    public synchronized static MongoDBConfiguration _load() throws ConfigurationException, IOException  {
        if (null == _instance) _instance = new MongoDBConfiguration();
        return _instance;
    }

    private Configuration config = null;
    private ConcurrentMap<String, MongoClient> clients;
    private ConcurrentMap<String, DB> dbMaps;

    public MongoDBConfiguration() throws ConfigurationException, IOException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.properties()
                        .setFileName(BaseConfiguration.getMongodbConfigFile())
                        .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
        config = builder.getConfiguration();
        clients = new ConcurrentHashMap<>();
        dbMaps = new ConcurrentHashMap<>();
    }

    public synchronized MongoClient getMongoClient(String name) throws UnknownHostException {
        MongoClient client = clients.get(name);
        if (null == client) {
            String keyPath = "data.mongodb." + name;
            String username = config.getString(keyPath + ".username");
            String password = config.getString(keyPath + ".password");
            String dbName = config.getString(keyPath + ".database");
            List<String> addressList = config.getList(String.class, keyPath + ".address");
            List<ServerAddress> serverAddresses = new ArrayList<>();
            for (String s : addressList) {
                String[] split = s.split(":");
                serverAddresses.add(new ServerAddress(split[0], Integer.parseInt(split[1])));
            }
            if (StringUtil.isNullOrEmpty(username) || StringUtil.isNullOrEmpty(password)
                    || StringUtil.isNullOrEmpty(dbName)) {
                client = new MongoClient(serverAddresses);
            } else {
                MongoCredential credential = MongoCredential
                        .createCredential(username, dbName, password.toCharArray());
                client = new MongoClient(serverAddresses, Arrays.asList(credential));
            }
            clients.put(name, client);
        }
        return client;
    }

    public DB getMongoDB(String clientName, String dbName) throws UnknownHostException {
        MongoClient mongoClient = getMongoClient(clientName);
        return mongoClient.getDB(dbName);
    }

}
