package com.etybeno.google.drive.model.enums;

/**
 * Created by thangpham on 19/04/2018.
 */
public enum Type {

    user("user"),
    group("group"),
    domain("domain"),
    anyone("anyone");

    private String value;

    private Type(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public String getValue() {
        return this.value;
    }
}
