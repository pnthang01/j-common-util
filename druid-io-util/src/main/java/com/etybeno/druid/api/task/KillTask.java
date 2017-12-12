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
public class KillTask extends Task {

    private String id;
    private String dataSource;
    private String interval;
    
    public KillTask() {
        super("kill");
    }

    @Deprecated
    public KillTask id(String id) {
        this.id = id;
        return this;
    }

    public KillTask datasource(String dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public KillTask interval(String interval) {
        this.interval = interval;
        return this;
    }
}
