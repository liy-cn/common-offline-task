package org.storm.commons.offlinetask.common;

public class Response<T> {

    private Integer code;

    private String message;

    private T data;

    public Response<T> success() {
        this.setResponseCode(ResponseCodeEnum.SUCCESS);
        return this;
    }

    public Response<T> success(T data) {
        this.setResponseCode(ResponseCodeEnum.SUCCESS);
        this.setData(data);
        return this;
    }

    public Response<T> failure(ResponseCodeEnum responseCode) {
        this.setResponseCode(responseCode);
        return this;
    }

    public Response<T> failure(String message) {
        this.setCode(ResponseCodeEnum.FAILURE.code());
        this.setMessage(message);
        return this;
    }

    public Response<T> failure(ResponseCodeEnum responseCode, T data) {
        this.setResponseCode(responseCode);
        this.setData(data);
        return this;
    }

    public Response<T> setResponseCode(ResponseCodeEnum responseCode) {
        this.code = responseCode.code();
        this.message = responseCode.message();
        return this;
    }


    public Integer getCode() {
        return code;
    }

    public Response<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }
}
