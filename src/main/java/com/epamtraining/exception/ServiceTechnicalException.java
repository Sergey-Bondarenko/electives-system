package com.epamtraining.exception;

/**
 * An exception that Service classes throw
 * @author Sergey Bondarenko
 */
public class ServiceTechnicalException extends Exception {

    public ServiceTechnicalException(){
    }

    public ServiceTechnicalException(String message) {
        super(message);
    }

    public ServiceTechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceTechnicalException(Throwable cause) {
        super(cause);
    }

    public ServiceTechnicalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
