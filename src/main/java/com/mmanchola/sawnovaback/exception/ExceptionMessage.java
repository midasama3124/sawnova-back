package com.mmanchola.sawnovaback.exception;

import org.springframework.util.StringUtils;

public enum ExceptionMessage {
    INVALID("is invalid (misspelled)"),
    MISSING("is missing (mandatory field)"),
    UNAVAILABLE("is already registered"),
    MISSING_INVALID("is invalid or non-existent"),
    MISSING_UNAVAILABLE("is missing or already taken"),
    INVALID_UNAVAILABLE("is invalid or unavailable"),
    ALL("is missing, invalid or unavailable"),
    NOT_FOUND("is not registered"),
    EXCEEDED("has exceeded");

    private String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMsg(String tableField) {
        return StringUtils.capitalize(tableField) + " " + this.message;
    }
}
