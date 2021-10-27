package org.storm.commons.offlinetask.exception;

public class SystemException extends BaseException {

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException() {
    }

    public SystemException(int code) {
        super(code);
    }

    public SystemException(int code, String message) {
        super(code, message);
    }
}
