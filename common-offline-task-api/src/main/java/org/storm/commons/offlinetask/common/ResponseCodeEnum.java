package org.storm.commons.offlinetask.common;

public enum ResponseCodeEnum {

    /**
     * 成功状态码
     */
    SUCCESS(0, "成功"),
    /**
     * 失败
     */
    FAILURE(1, "失败"),
;

    private Integer code;

    private String message;

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (ResponseCodeEnum item : ResponseCodeEnum.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ResponseCodeEnum item : ResponseCodeEnum.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
