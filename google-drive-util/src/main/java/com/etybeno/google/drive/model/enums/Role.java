package com.etybeno.google.drive.model.enums;

/**
 * Created by thangpham on 19/04/2018.
 */
public enum Role {

    organizer("organizer"),
    writer("writer"),
    commenter("commenter"),
    reader("reader");

    private String value;

    private Role(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public String getValue() {
        return this.value;
    }
}
