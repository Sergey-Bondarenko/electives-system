package com.epamtraining.exception;

/**
 * Auth exception
 */
public class ServiceLogicalException extends Exception {

    public ServiceLogicalException(){
    }

    public ServiceLogicalException(String message) {
        super(message);
    }

    public ServiceLogicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceLogicalException(Throwable cause) {
        super(cause);
    }

    public ServiceLogicalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
