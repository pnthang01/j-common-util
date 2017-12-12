/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.filter;

import com.etybeno.druid.api.search.Search;

/**
 *
 * @author thangpham
 */
public class SearchFilter extends DimensionFilter<SearchFilter> {

    private Search query;

    public SearchFilter() {
        this.type = "search";
    }

    public SearchFilter query(Search query) {
        this.query = query;
        return this;
    }

}
