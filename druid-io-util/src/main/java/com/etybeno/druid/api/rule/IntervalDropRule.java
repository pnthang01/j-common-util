/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.rule;

/**
 *
 * @author thangpham
 */
public class IntervalDropRule extends Rule {

    private String interval;

    public IntervalDropRule() {
        super("dropByInterval");
    }

    public IntervalDropRule interval(String interval) {
        this.interval = interval;
        return this;
    }

}
