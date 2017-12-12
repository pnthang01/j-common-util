/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.search;

/**
 *
 * @author thangpham
 */
public class ContainSearch extends Search {

    private boolean case_sensitive;
    private String value;

    public ContainSearch() {
        this.type = "contains";
    }

    public ContainSearch caseSensitive(boolean sensitive) {
        this.case_sensitive = sensitive;
        return this;
    }

    public ContainSearch value(String value) {
        this.value = value;
        return this;
    }

}
