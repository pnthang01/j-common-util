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
public class RegexSearch extends Search {

    private String pattern;

    public RegexSearch() {
        this.type = "regex";
    }

    public RegexSearch pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }
}
