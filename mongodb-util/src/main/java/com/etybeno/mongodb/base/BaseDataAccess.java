package com.etybeno.mongodb.base;

import com.etybeno.mongodb.annotation.Collection;
import com.etybeno.mongodb.config.MongoDBConfiguration;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BasicBSONObject;
import org.bson.Document;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import static com.etybeno.common.util.StringUtil.OBJECT_MAPPER;

/**
 * Created by thangpham on 07/12/2017.
 */
public abstract class BaseDataAccess<T> {

    static final Logger LOGGER = LogManager.getLogger(BaseDataAccess.class);

    private String clientName;
    private String collectionName;
    private Class<T> typeArgument;
    private MongoDatabase db;

    protected BaseDataAccess(String clientName) throws Exception {
        this.clientName = clientName;
        this.db = MongoDBConfiguration._load().getMongoDB(clientName);

        Type superclass = getClass().getGenericSuperclass();
        ParameterizedType parameterized = (ParameterizedType) superclass;
        // with nested generic types, this becomes a little more complicated
        typeArgument = (Class<T>) parameterized.getActualTypeArguments()[0];
        collectionName = typeArgument.getAnnotation(Collection.class).name();
        //
        if(!checkCollectionExist()) initCollection();
    }

    protected abstract void initCollection();

    protected <T> T convertToObject(DBObject dbObject) {
        try {
            String s = OBJECT_MAPPER.writeValueAsString(dbObject.toMap());
            return OBJECT_MAPPER.readValue(s, (Class<T>) typeArgument);
        } catch (Exception ex) {
            LOGGER.error("Could not convert to object", ex);
        }
        return null;
    }

    public Long getNextLongSequence(String name) {
        Document newId = getNonTypeCollection().findOneAndUpdate(
                Filters.eq("_id", name),
                Updates.inc("seq", 1),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER).upsert(true).maxTime(0, TimeUnit.SECONDS));
        return Long.parseLong(newId.get("seq").toString());
    }


    public Integer getNextIntegerSequence(String name) {
        Document newId = getNonTypeCollection().findOneAndUpdate(
                Filters.eq("_id", name),
                Updates.inc("seq", 1),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER).upsert(true).maxTime(0, TimeUnit.SECONDS));
        return Integer.parseInt(newId.get("seq").toString());
    }

    protected MongoCollection<T> getCollection() {
        return db.getCollection(collectionName, typeArgument);
    }

    protected MongoCollection<Document> getNonTypeCollection() {
        return db.getCollection(collectionName);
    }

    protected boolean checkCollectionExist() {
        for(String name : db.listCollectionNames()) {
            if(name.equalsIgnoreCase(collectionName)) return true;
        }
        return false;
    }
}
