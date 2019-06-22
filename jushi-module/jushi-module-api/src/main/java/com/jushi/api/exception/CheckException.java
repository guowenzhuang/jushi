package com.jushi.api.exception;

import lombok.Data;

/**
 * @author 80795
 */
@Data
public class CheckException extends RuntimeException {
    /**
     * 出错字段名字
     */
    private String fieldName;
    /**
     * 出错字段值
     */
    private String fieldValue;
    /**
     * 错误描述
     */
    private String description;


    public CheckException(String message) {
        super(message);
    }

    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckException(Throwable cause) {
        super(cause);
    }

    protected CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CheckException() {
        super();
    }

    public CheckException(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public CheckException(String fieldName, String fieldValue,String description) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.description = description;
    }
}
