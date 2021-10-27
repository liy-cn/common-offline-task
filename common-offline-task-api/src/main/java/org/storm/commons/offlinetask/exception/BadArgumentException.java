package org.storm.commons.offlinetask.exception;

public class BadArgumentException extends BaseException {

    public BadArgumentException() {
        super();
    }

    public BadArgumentException(String message) {
        super(message);
    }

    public BadArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadArgumentException(Throwable cause) {
        super(cause);
    }

    public BadArgumentException(int code) {
        super(code);
    }

    public BadArgumentException(int code, String message) {
        super(code, message);
    }
}
