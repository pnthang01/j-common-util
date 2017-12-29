package com.etybeno.common.model;

import com.etybeno.common.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by thangpham on 13/12/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDataModel {

    private Status status;
    private String message;
    private Long count;
    private Object data;

    public Status getStatus() {
        return status;
    }

    public ResponseDataModel setStatus(Status status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseDataModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseDataModel setData(Object data) {
        this.data = data;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public ResponseDataModel setCount(Long count) {
        this.count = count;
        return this;
    }
}