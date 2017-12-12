/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.having;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thangpham
 */
public abstract class LogicalHaving<T> extends Having {

    private List<Having> havingSpecs;

    public T havingSpecs(Having... clauses) {
        if (null == havingSpecs) {
            this.havingSpecs = new ArrayList();
        }
        this.havingSpecs.addAll(Arrays.asList(clauses));
        return (T) this;
    }

    public T havingSpecs(List<Having> clauses) {
        this.havingSpecs = clauses;
        return (T) this;
    }
}
