package com.etybeno.mongodb.base;

import com.etybeno.mongodb.annotation.Collection;
import com.etybeno.mongodb.config.MongoDBConfiguration;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * Created by thangpham on 07/12/2017.
 */
public abstract class BaseDataAccess<T> {

    private String clientName;
    private String dbName;
    private String collectionName;
    private DB db;

    protected BaseDataAccess(String clientName, String dbName) throws Exception {
        this.clientName = clientName;
        this.dbName = dbName;
        this.db = MongoDBConfiguration._load().getMongoDB(clientName, dbName);

        Type superclass = getClass().getGenericSuperclass();
        ParameterizedType parameterized = (ParameterizedType) superclass;
        // with nested generic types, this becomes a little more complicated
        Class<?> typeArgument = (Class<?>) parameterized.getActualTypeArguments()[0];
        collectionName = typeArgument.getAnnotation(Collection.class).name();
    }

    protected abstract <T> T convertToObject(DBObject dbObject);

    protected Long getNextLongSequence(String name) {
        DBObject newId = getCollection().findAndModify(new BasicDBObject("_id", name), null, null, false,
                new BasicDBObject("$inc", new BasicDBObject("seq", 1)), true, true, 0, TimeUnit.SECONDS);
        return Long.parseLong(newId.get("seq").toString());
    }

    protected Integer getNextIntegerSequence(String name) {
        DBObject newId = getCollection().findAndModify(new BasicDBObject("_id", name), null, null, false,
                new BasicDBObject("$inc", new BasicDBObject("seq", 1)), true, true, 0, TimeUnit.SECONDS);
        return Integer.parseInt(newId.get("seq").toString());
    }

    protected DBCollection getCollection() {
        return db.getCollection(collectionName);
    }

}
