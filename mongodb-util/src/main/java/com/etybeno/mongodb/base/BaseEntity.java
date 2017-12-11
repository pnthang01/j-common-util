package com.etybeno.mongodb.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Created by thangpham on 07/12/2017.
 */
public abstract class BaseEntity implements Comparable, Cloneable, Serializable{

    @JsonIgnore
    protected Object _id;

    public Object get_id() {
        return _id;
    }

    public void set_id(Object _id) {
        this._id = _id;
    }
}
