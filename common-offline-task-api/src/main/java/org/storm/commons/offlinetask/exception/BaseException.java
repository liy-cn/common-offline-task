package org.storm.commons.offlinetask.exception;

import java.io.Serializable;

public class BaseException extends RuntimeException implements Serializable {

    private int code;

    public BaseException() {
    }
    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(int code) {
        this.code = code;
    }

    public BaseException(int code, String message)  {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
