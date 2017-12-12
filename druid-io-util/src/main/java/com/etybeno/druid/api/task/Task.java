/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.task;

/**
 *
 * @author thangpham
 */
public abstract class Task {
    private final String type;

    protected Task(String type) {
        this.type = type;
    }
    
}
