package com.etybeno.mongodb.model;

import com.etybeno.common.util.StringUtil;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thangpham on 08/02/2018.
 */
public class MongoClientInfo {

    private String username;
    private String password;
    private String dbName;
    private List<ServerAddress> addressList;

    private MongoClient mongoClient;

    public MongoClientInfo() {
    }

    public MongoClientInfo(String username, String password, String dbName, List<ServerAddress> addressList) {
        this.username = username;
        this.password = password;
        this.dbName = dbName;
        this.addressList = addressList;
    }

    public synchronized MongoDatabase getDatabase() {
        if(null == mongoClient) {
            if (StringUtil.isNullOrEmpty(username) || StringUtil.isNullOrEmpty(password) || StringUtil.isNullOrEmpty(dbName)) {
                mongoClient = new MongoClient(addressList);
            } else {
                MongoCredential credential = MongoCredential
                        .createCredential(username, dbName, password.toCharArray());
                mongoClient = new MongoClient(addressList, credential, MongoClientOptions.builder().build());
            }
        }
        return mongoClient.getDatabase(dbName);
    }

    public String getUsername() {
        return username;
    }

    public MongoClientInfo setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MongoClientInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDbName() {
        return dbName;
    }

    public MongoClientInfo setDbName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    public List<ServerAddress> getAddressList() {
        return addressList;
    }

    public MongoClientInfo setAddressList(List<ServerAddress> addressList) {
        this.addressList = addressList;
        return this;
    }

    public MongoClientInfo addAddress(ServerAddress serverAddress) {
        if(null == addressList) addressList = new ArrayList<>();
        addressList.add(serverAddress);
        return this;
    }
}
