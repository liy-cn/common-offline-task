package org.storm.commons.offlinetask.exception;

public class BusinessException extends BaseException {
    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(int code) {
        super(code);
    }

    public BusinessException(int code, String message) {
        super(code, message);
    }
}
