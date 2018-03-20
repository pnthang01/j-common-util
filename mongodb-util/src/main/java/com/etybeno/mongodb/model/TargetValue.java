package com.etybeno.mongodb.model;

/**
 * Created by thangpham on 11/03/2018.
 */
public class TargetValue implements KeyValue {

    private String target;
    private Object value;

    public TargetValue(String target, Object value) {
        this.target = target;
        this.value = value;
    }

    public TargetValue() {
    }

    public String getTarget() {
        return target;
    }

    public TargetValue setTarget(String target) {
        this.target = target;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public TargetValue setValue(Object value) {
        this.value = value;
        return this;
    }
}
