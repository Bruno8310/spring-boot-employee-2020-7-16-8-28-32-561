package com.thoughtworks.springbootemployee.constant;

public enum ExceptionErrorMessage {

    NOT_FOUND("Data Not Found!"),
    ILLEGAL_OPERATION("Illegal Operation!");

    public String errorMessage;

    ExceptionErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

}
