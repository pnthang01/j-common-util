/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;


import com.etybeno.druid.api.search.*;

/**
 *
 * @author thangpham
 */
public class SearchBuilders {

    public static ContainSearch containSearch() {
        return new ContainSearch();
    }
    
    public static FragmentSearch fragmentSearch() {
        return new FragmentSearch();
    }
    
    public static InsensitiveContainSearch insenContainSearch() {
        return new InsensitiveContainSearch();
    }
    
    public static RegexSearch regexSearch() {
        return new RegexSearch();
    }
}
