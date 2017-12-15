package com.etybeno.druid.api.query;

import com.etybeno.druid.api.Response;
import com.etybeno.druid.api.granularity.IGranularity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by thangpham on 14/12/2017.
 */
public abstract class TimeQuery<I extends TimeQuery, T extends Response> extends Query<I, T> {

    private List<String> intervals;
    private IGranularity granularity;

    public final I granularity(IGranularity granularity) {
        this.granularity = granularity;
        return (I) this;
    }


    public final I intervals(String... intervals) {
        this.intervals = Arrays.asList(intervals);
        return (I) this;
    }

    public final I intervals(Collection<String> intervals) {
        this.intervals = new ArrayList<>(intervals);
        return (I) this;
    }

    public final I addIntervals(String... intervals) {
        if (this.intervals == null) {
            this.intervals = new ArrayList();
        }
        this.intervals.addAll(Arrays.asList(intervals));
        return (I) this;
    }
}
