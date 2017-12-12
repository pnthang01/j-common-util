/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.ingestion;

/**
 *
 * @author thangpham
 */
public class StringParser extends Parser {

    private String type = "string";
    private ParseSpec parseSpec;

    public StringParser parseSpec(ParseSpec parseSpec) {
        this.parseSpec = parseSpec;
        return this;
    }
    
    public ParseSpec getParseSpec() {
        return parseSpec;
    }
}
