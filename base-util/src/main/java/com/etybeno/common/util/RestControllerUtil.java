package com.etybeno.common.util;

import com.etybeno.common.enums.Status;
import com.etybeno.common.model.Pager;
import com.etybeno.common.model.ResponseDataModel;

/**
 * Created by thangpham on 30/04/2018.
 */
public class RestControllerUtil {

    public static ResponseDataModel responseOK() {
        return new ResponseDataModel().setStatus(Status.SUCCESS).setData("OK");
    }

    public static ResponseDataModel responseError(String errorMessage) {
        return new ResponseDataModel().setStatus(Status.ERROR).setMessage(errorMessage).setCode(6000);
    }

    public static ResponseDataModel responseFailed(String failedMessage) {
        return new ResponseDataModel().setStatus(Status.FAILED).setMessage(failedMessage).setCode(6000);
    }

    public static ResponseDataModel responseFailed(int code, String failedMessage) {
        return new ResponseDataModel().setStatus(Status.FAILED).setMessage(failedMessage).setCode(code);
    }

    public static ResponseDataModel responseData(Object data) {
        return new ResponseDataModel().setStatus(Status.SUCCESS).setData(data);
    }

    public static ResponseDataModel responseData(Object data, long count) {
        return new ResponseDataModel().setStatus(Status.SUCCESS).setData(data).setPager(new Pager(count));
    }

    public static ResponseDataModel responseData(Object data, int totalPage, int currentPage, int showPages, long count) {
        return new ResponseDataModel().setStatus(Status.SUCCESS).setData(data).setPager(
                new Pager(totalPage, currentPage, showPages, count));
    }

    public static ResponseDataModel responseCode(int code) {
        return new ResponseDataModel().setStatus(Status.SUCCESS).setCode(code);
    }

    public static ResponseDataModel responseData(int code, Object data) {
        return new ResponseDataModel().setStatus(Status.SUCCESS).setData(data).setCode(code);
    }
}