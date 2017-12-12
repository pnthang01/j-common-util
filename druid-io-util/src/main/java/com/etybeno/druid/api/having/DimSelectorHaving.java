package com.etybeno.druid.api.having;

/**
 * Created by thangpham on 26/06/2017.
 */
public class DimSelectorHaving extends Having {

    private String type = "dimSelector";
    private String dimension;
    private String value;

    public DimSelectorHaving dimension(String dimension) {
        this.dimension = dimension;
        return this;
    }

    public DimSelectorHaving value(String value) {
        this.value = value;
        return this;
    }
}
