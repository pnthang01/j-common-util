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
public abstract class Rule {

    protected String type;

    protected Rule(String type) {
        this.type = type;
    }
}
