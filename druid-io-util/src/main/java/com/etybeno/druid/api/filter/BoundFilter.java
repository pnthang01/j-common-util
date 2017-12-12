/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.filter;

/**
 *
 * @author thangpham
 */
public class BoundFilter extends DimensionFilter<BoundFilter> {

    private String lower;
    private boolean lowerStrict;
    private String upper;
    private boolean upperStrict;
    private boolean alphaNumeric;

    public BoundFilter() {
        this.type = "bound";
    }

    public BoundFilter lower(String lower) {
        this.lower = lower;
        return this;
    }

    public BoundFilter lowerStrict(boolean lowerStrict) {
        this.lowerStrict = lowerStrict;
        return this;
    }

    public BoundFilter upper(String upper) {
        this.upper = upper;
        return this;
    }

    public BoundFilter upperStrict(boolean upperStrict) {
        this.upperStrict = upperStrict;
        return this;
    }

    public BoundFilter alphaNumeric(boolean alphaNumeric) {
        this.alphaNumeric = alphaNumeric;
        return this;
    }

}
