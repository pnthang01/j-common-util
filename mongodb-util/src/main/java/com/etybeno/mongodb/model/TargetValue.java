package com.etybeno.mongodb.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by thangpham on 23/02/2018.
 */
public class TargetValue {

    private String target;
    private Set<Object> values = new HashSet<>();

    public TargetValue() {
    }

    public TargetValue(String target, Set<Object> values) {
        this.target = target;
        this.values = values;
    }

    public TargetValue(String target, Object... values) {
        this.target = target;
        this.values.addAll(Arrays.asList(values));
    }

    public TargetValue(String target, Collection<Object> values) {
        this.target = target;
        this.values.addAll(values);
    }

    public String getTarget() {
        return target;
    }

    public TargetValue setTarget(String target) {
        this.target = target;
        return this;
    }

    public Set<Object> getValues() {
        return values;
    }

    public TargetValue setValues(Set<Object> values) {
        synchronized (this.values) {
            this.values = values;
        }
        return this;
    }

    public TargetValue addValues(Object... values) {
        this.values.addAll(Arrays.asList(values));
        return this;
    }
}

