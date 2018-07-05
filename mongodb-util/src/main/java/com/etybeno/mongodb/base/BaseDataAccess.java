package com.etybeno.mongodb.base;

import com.etybeno.mongodb.annotation.Collection;
import com.etybeno.mongodb.config.MongoDBConfiguration;
import com.etybeno.mongodb.model.KeyValue;
import com.etybeno.mongodb.model.TargetValue;
import com.etybeno.mongodb.model.TargetValues;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by thangpham on 07/12/2017.
 */
public abstract class BaseDataAccess<T> {

    static final Logger LOGGER = LoggerFactory.getLogger(BaseDataAccess.class);

    protected String collectionName;
    protected Class<T> typeArgument;
    protected MongoDatabase db;

    /**
     * This abstract collection is used to initialize collection in MongoDB if it hasn't existed
     */
    protected abstract void initCollection();

    protected Bson buildProjection(int projectionType) {
        return null;
    }

    protected BaseDataAccess(String clientName) throws Exception {
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
     * @param seqName
     * @return
     */
    public Long getNextLongSequence(String name, String seqName) {
        Document newId = getNonTypeCollection().findOneAndUpdate(
                Filters.eq("_id", name),
                Updates.inc(seqName, 1),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER).upsert(true).maxTime(0, TimeUnit.SECONDS));
        return Long.parseLong(newId.get(seqName).toString());
    }

    /**
     * Get a next integer sequence, this sequence will be stored in its own collection
     *
     * @param name
     * @param seqName
     * @return
     */
    public Integer getNextIntegerSequence(String name, String seqName) {
        Document newId = getNonTypeCollection().findOneAndUpdate(
                Filters.eq("_id", name),
                Updates.inc(seqName, 1),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER).upsert(true).maxTime(0, TimeUnit.SECONDS));
        return Integer.parseInt(newId.get(seqName).toString());
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
    public long count(List<KeyValue> targets) {
        if (null == targets || targets.isEmpty()) return getCollection().count();
        else return getCollection().count(Filters.and(buildFilters(targets)));
    }

    public <O> O getOne(List<KeyValue> targets,int projectionType, @Nonnull Class<O> outputClass) {
        List<Bson> aggregates = new ArrayList();
        if (targets != null && !targets.isEmpty()) aggregates.add(Aggregates.match(Filters.and(buildFilters(targets))));
        if (projectionType > -1) aggregates.add(Aggregates.project(buildProjection(projectionType)));
        return getCollection().aggregate(aggregates, outputClass).first();
    }

    /**
     * Simple query to get an object in collection. All target values only support
     * affirmative conditions.
     *
     * @param targets
     * @return
     */
    public T getOne(List<KeyValue> targets) {
        return getCollection().find(Filters.and(buildFilters(targets))).first();
    }

    /**
     * Simple query to get a raw data (#Document class) in collection. All target values only
     * support affirmative conditions.
     *
     * @param targets
     * @param projectionType
     * @return
     */
    public Document getRawOne(List<KeyValue> targets, int projectionType) {
        List<Bson> aggregates = new ArrayList();
        if (targets != null && !targets.isEmpty()) aggregates.add(Aggregates.match(Filters.and(buildFilters(targets))));
        if (projectionType > -1) aggregates.add(Aggregates.project(buildProjection(projectionType)));
        return getNonTypeCollection().aggregate(aggregates).first();
    }

    /**
     * Simple query to get a list of objects in collection. All target values only support
     * affirmative conditions.
     *
     * @param targets
     * @return
     */
    public List<T> getAll(List<KeyValue> targets) {
        FindIterable<T> ts = getCollection().find(Filters.and(buildFilters(targets)));
        List<T> rs = new ArrayList<>();
        for (T t : ts) rs.add(t);
        return rs;
    }

    /**
     * Query method to get a list of objects in collection, which is paginated and sorted All target values only support
     * affirmative conditions. The outcome will base on #outputClass, remember that this result only work for properties in collection.
     *
     * @param targets
     * @param from
     * @param size
     * @param sorts
     * @param projectionType
     * @param outputClass
     * @param <O>
     * @return
     */
    public <O> List<O> getAll(List<KeyValue> targets, int from, int size, Map<String, Object> sorts, int projectionType, @Nonnull Class<O> outputClass) {
        List<Bson> aggregates = new ArrayList();
        if (targets != null && !targets.isEmpty()) aggregates.add(Aggregates.match(Filters.and(buildFilters(targets))));
        if (sorts != null && !sorts.isEmpty()) aggregates.add(Aggregates.sort(new Document(sorts)));
        if (from > -1) aggregates.add(Aggregates.skip(from));
        if (size > -1) aggregates.add(Aggregates.limit(size));
        if (projectionType > -1) aggregates.add(Aggregates.project(buildProjection(projectionType)));
        AggregateIterable<O> aggregate = getCollection().aggregate(aggregates, outputClass);
        List<O> result = new ArrayList();
        for (O t : aggregate) result.add(t);
        return result;
    }

    public List<Document> getRawAll(List<KeyValue> targets, int from, int size, List<TargetValue> sorts, int projectionType) {
        List<Bson> aggregates = new ArrayList();
        if (targets != null && !targets.isEmpty()) aggregates.add(Aggregates.match(Filters.and(buildFilters(targets))));
        if (sorts != null && !sorts.isEmpty()) {
            Map<String, Object> sortMap = sorts.stream().collect(Collectors.toMap(o -> o.getTarget(), o -> o.getValue()));
            aggregates.add(Aggregates.sort(new Document(sortMap)));
        }
        if (from > -1) aggregates.add(Aggregates.skip(from));
        if (size > -1) aggregates.add(Aggregates.limit(size));
        if (projectionType > -1) aggregates.add(Aggregates.project(buildProjection(projectionType)));
        AggregateIterable<Document> aggregate = getNonTypeCollection().aggregate(aggregates);
        List<Document> result = new ArrayList();
        for (Document t : aggregate) result.add(t);
        return result;
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