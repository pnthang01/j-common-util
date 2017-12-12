/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api;

import com.etybeno.druid.api.rule.IntervalDropRule;

/**
 *
 * @author thangpham
 */
public class RuleBuilders {

    public static IntervalDropRule intervalDropRule() {
        return new IntervalDropRule();
    }
}
