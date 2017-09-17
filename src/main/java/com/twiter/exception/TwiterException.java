package com.twiter.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by kb on 2017-09-15.
 */
public class TwiterException extends RuntimeException {

    protected String errorMessage;

    protected HttpStatus httpStatus;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
