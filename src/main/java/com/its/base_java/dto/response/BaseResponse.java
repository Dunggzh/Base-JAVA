package com.its.base_java.dto.response;

import com.its.base_java.component.Translator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> implements Serializable {

    private int code;
    private T data;
    private String message = Translator.toLocale("label_success");
    private Long totalRecord;
    private Map<String, Object> errors;
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPage;

    public BaseResponse() {
    }

    public BaseResponse(String message) {
        this.message = message;
    }
    public BaseResponse(Map<String, Object> errors, String message) {
        this.errors = errors;
        this.message = message;
    }

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(T data, String message) {
        this.message = message;
        this.data = data;
    }

    public BaseResponse(T data, long totalRecord) {
        this.data = data;
        this.totalRecord = totalRecord;
    }

    public BaseResponse(T data, long totalRecord, String message) {
        this.message = message;
        this.data = data;
        this.totalRecord = totalRecord;
    }

    public BaseResponse(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public BaseResponse(T data, long totalRecord, Integer currentPage, Integer pageSize, Integer totalPage) {
        this.data = data;
        this.totalRecord = totalRecord;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
    }

}
