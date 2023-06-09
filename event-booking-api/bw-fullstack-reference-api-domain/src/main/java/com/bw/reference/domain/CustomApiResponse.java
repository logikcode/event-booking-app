package com.bw.reference.domain;

import java.util.HashMap;

public class CustomApiResponse<T> {
    private String statusMsg;
    private String statusCode;
    private String responseCode;
    private Integer code;
    private String message;
    private T data;
    private HashMap<String, Object> meta = new HashMap<>();
    private String dateGenerated;
    private String status;

    public CustomApiResponse() {
    }

    public CustomApiResponse(String statusCode, String statusMsg, T data) {
        this.statusMsg = statusMsg;
        this.statusCode = statusCode;
        this.data = data;
    }

    public CustomApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CustomApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomApiResponse(int code, String message, T data, String dateGenerated) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.dateGenerated = dateGenerated;
    }


    public String getStatusMsg() {
        return statusMsg;
    }

    public com.bw.reference.domain.CustomApiResponse<T> setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
        return this;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public com.bw.reference.domain.CustomApiResponse<T> setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public com.bw.reference.domain.CustomApiResponse<T> setResponseCode(String responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public com.bw.reference.domain.CustomApiResponse<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public com.bw.reference.domain.CustomApiResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public com.bw.reference.domain.CustomApiResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public HashMap<String, Object> getMeta() {
        return meta;
    }

    public com.bw.reference.domain.CustomApiResponse<T> setMeta(HashMap<String, Object> meta) {
        this.meta = meta;
        return this;
    }

    public String getDateGenerated() {
        return dateGenerated;
    }

    public com.bw.reference.domain.CustomApiResponse<T> setDateGenerated(String dateGenerated) {
        this.dateGenerated = dateGenerated;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public com.bw.reference.domain.CustomApiResponse<T> setStatus(String status) {
        this.status = status;
        return this;
    }
}