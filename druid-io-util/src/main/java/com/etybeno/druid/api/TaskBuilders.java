/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;

import com.etybeno.druid.api.task.KillTask;

/**
 *
 * @author thangpham
 */
public class TaskBuilders {
    
    public static KillTask killTask() {
        return new KillTask();
    } 
}
