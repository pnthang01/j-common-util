package com.etybeno.mongodb.config;

import com.etybeno.common.config.BaseConfiguration;
import com.etybeno.mongodb.model.MongoClientInfo;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Created by thangpham on 27/11/2017.
 */
public class MongoDBConfiguration {

    private static MongoDBConfiguration _instance;

    public synchronized static MongoDBConfiguration _load() throws ConfigurationException, IOException {
        if (null == _instance) _instance = new MongoDBConfiguration();
        return _instance;
    }

    private Configuration config = null;
    private ConcurrentMap<String, MongoClientInfo> clients;

    public MongoDBConfiguration() throws ConfigurationException, IOException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.properties()
                        .setFileName(BaseConfiguration.getMongodbConfigFile())
                        .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
        config = builder.getConfiguration();
        clients = new ConcurrentHashMap<>();
    }

    public synchronized MongoClientInfo getMongoClient(String name) throws UnknownHostException {
        MongoClientInfo client = clients.get(name);
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
            client = new MongoClientInfo(username, password, dbName, serverAddresses);
            clients.put(name, client);
        }
        return client;
    }

    public MongoDatabase getMongoDB(String clientName) throws UnknownHostException {
        MongoClientInfo mongoClient = getMongoClient(clientName);
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        return mongoClient.getDatabase().withCodecRegistry(pojoCodecRegistry);
    }

}