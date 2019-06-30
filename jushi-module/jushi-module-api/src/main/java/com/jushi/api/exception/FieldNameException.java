package com.jushi.api.exception;

import lombok.Data;

/**
 * 字段名称错误
 *
 * @author 80795
 * @date 2019/6/29 14:51
 */
@Data
public class FieldNameException extends RuntimeException {
    /**
     * 应为字段名
     */
    private String fieldName;
    /**
     * 现在字段名
     */
    private String nowFieldName;
    /**
     * 错误描述
     */
    private String description;

    public FieldNameException(String fieldName, String nowFieldName, String description) {
        this.fieldName = fieldName;
        this.nowFieldName = nowFieldName;
        this.description = description;
    }

    public FieldNameException(String fieldName, String nowFieldName) {
        this.fieldName = fieldName;
        this.nowFieldName = nowFieldName;
    }

    public FieldNameException(String message) {
        super(message);
    }

    public FieldNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldNameException(Throwable cause) {
        super(cause);
    }

    protected FieldNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
