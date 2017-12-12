/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etybeno.druid.api.query;


import com.etybeno.druid.api.Response;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author thangpham
 */
public class SelectResponse implements Response {

    private String timestamp;
    private SelectResult result;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public SelectResult getResult() {
        return result;
    }

    public void setResult(SelectResult result) {
        this.result = result;
    }

    public static class SelectResult {

        private Map<String, Integer> pagingIdentifiers;
        private List<EventResult> events;

        public Map<String, Integer> getPagingIdentifiers() {
            return pagingIdentifiers;
        }

        public void setPagingIdentifiers(Map<String, Integer> pagingIdentifiers) {
            this.pagingIdentifiers = pagingIdentifiers;
        }

        public List<EventResult> getEvents() {
            return events;
        }

        public void setEvents(List<EventResult> events) {
            this.events = events;
        }

    }

    public static class EventResult {

        private String segmentId;
        private int offset;
        private TreeMap<String, Object> event;

        public String getSegmentId() {
            return segmentId;
        }

        public void setSegmentId(String segmentId) {
            this.segmentId = segmentId;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public TreeMap<String, Object> getEvent() {
            return event;
        }

        public void setEvent(TreeMap<String, Object> event) {
            this.event = event;
        }

    }
}
