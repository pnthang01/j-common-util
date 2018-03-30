package com.etybeno.mongodb.base;

import com.etybeno.mongodb.annotation.Collection;
import com.etybeno.mongodb.config.MongoDBConfiguration;
import com.etybeno.mongodb.model.KeyValue;
import com.etybeno.mongodb.model.TargetValue;
import com.etybeno.mongodb.model.TargetValues;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by thangpham on 07/12/2017.
 */
public abstract class BaseDataAccess<T> {

    static final Logger LOGGER = LogManager.getLogger(BaseDataAccess.class);

    private String clientName;
    private String collectionName;
    private Class<T> typeArgument;
    private MongoDatabase db;

    /**
     * This abstract collection is used to initialize collection in MongoDB if it hasn't existed
     */
    protected abstract void initCollection();

    protected BaseDataAccess(String clientName) throws Exception {
        this.clientName = clientName;
        this.db = MongoDBConfiguration._load().getMongoDB(clientName);

        Type superclass = getClass().getGenericSuperclass();
        ParameterizedType parameterized = (ParameterizedType) superclass;
        // with nested generic types, this becomes a little more complicated
        typeArgument = (Class<T>) parameterized.getActualTypeArguments()[0];
        collectionName = typeArgument.getAnnotation(Collection.class).name();
        //
        if (!checkCollectionExist()) initCollection();
    }

    /**
     * Get a next long sequence, this sequence will be stored in its own collection
     *
     * @param name
     * @return
     */
    public Long getNextLongSequence(String name) {
        Document newId = getNonTypeCollection().findOneAndUpdate(
                Filters.eq("_id", name),
                Updates.inc("seq", 1),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER).upsert(true).maxTime(0, TimeUnit.SECONDS));
        return Long.parseLong(newId.get("seq").toString());
    }

    /**
     * Get a next integer sequence, this sequence will be stored in its own collection
     *
     * @param name
     * @return
     */
    public Integer getNextIntegerSequence(String name) {
        Document newId = getNonTypeCollection().findOneAndUpdate(
                Filters.eq("_id", name),
                Updates.inc("seq", 1),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER).upsert(true).maxTime(0, TimeUnit.SECONDS));
        return Integer.parseInt(newId.get("seq").toString());
    }

    /**
     * A typed connection, is used for POJO-mapped query
     *
     * @return
     */
    protected MongoCollection<T> getCollection() {
        return db.getCollection(collectionName, typeArgument);
    }

    /**
     * A non-typed connection, is used for query raw data
     *
     * @return
     */
    protected MongoCollection<Document> getNonTypeCollection() {
        return db.getCollection(collectionName);
    }

    /**
     * A method to check collection which is supported by this #BaseDataAccess
     *
     * @return
     */
    protected boolean checkCollectionExist() {
        for (String name : db.listCollectionNames()) {
            if (name.equalsIgnoreCase(collectionName)) return true;
        }
        return false;
    }

    /**
     * Simple query to count all objects by affirmative conditions.
     *
     * @param targets
     * @return
     */
    public long countByTargets(List<KeyValue> targets) {
        if (null == targets || targets.isEmpty())
            return getCollection().count();
        else
            return getCollection().count(Filters.and(buildFilters(targets)));
    }

    /**
     * Simple query to get an object in collection. All target values only support
     * affirmative conditions.
     *
     * @param targets
     * @return
     */
    public T getOneByTargets(List<KeyValue> targets) {
        return getCollection().find(Filters.and(buildFilters(targets))).first();
    }

    /**
     * Simple query to get an object in collection. All target values only support
     * affirmative conditions.
     * @param targets
     * @return
     */
    public T getOneByTargets(KeyValue... targets) {
        return getOneByTargets(Arrays.asList(targets));
    }

    /**
     * Simple query to get a list of objects in collection. All target values only support
     * affirmative conditions.
     *
     * @param targets
     * @return
     */
    public List<T> getAllByTargets(List<KeyValue> targets) {
        FindIterable<T> ts = getCollection().find(Filters.and(buildFilters(targets)));
        List<T> rs = new ArrayList<>();
        for (T t : ts) rs.add(t);
        return rs;
    }

    public boolean deleteById(Object id) {
        DeleteResult deleteResult = getCollection().deleteOne(Filters.eq("_id", id));
        return deleteResult.getDeletedCount() == 1;
    }

    public long deleteByIds(List<?> ids) {
        DeleteResult deleteResult = getCollection().deleteMany(Filters.in("_id", ids));
        return deleteResult.getDeletedCount();
    }

    protected List<Bson> buildFilters(List<KeyValue> targets) {
        return targets.stream()
                .map(o -> {
                    if (o instanceof TargetValues) {
                        TargetValues multi = (TargetValues) o;
                        return Filters.in(multi.getTarget(), multi.getValues());
                    } else {
                        TargetValue single = (TargetValue) o;
                        return Filters.eq(single.getTarget(), single.getValue());
                    }
                }).collect(Collectors.toList());
    }

}