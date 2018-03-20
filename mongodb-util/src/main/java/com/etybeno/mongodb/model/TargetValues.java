package com.etybeno.mongodb.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by thangpham on 23/02/2018.
 */
public class TargetValues implements KeyValue{

    private String target;
    private Set values = new HashSet<>();

    public TargetValues() {
    }

    public TargetValues(String target, Collection values) {
        this.target = target;
        this.values.addAll(values);
    }

    public String getTarget() {
        return target;
    }

    public TargetValues setTarget(String target) {
        this.target = target;
        return this;
    }

    public Set<Object> getValues() {
        return values;
    }

    public TargetValues setValues(Set values) {
        synchronized (this.values) {
            this.values = values;
        }
        return this;
    }

    public TargetValues addValues(Object... values) {
        this.values.addAll(Arrays.asList(values));
        return this;
    }
}

