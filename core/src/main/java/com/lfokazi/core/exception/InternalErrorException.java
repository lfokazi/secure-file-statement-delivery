package com.lfokazi.core.exception;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String message, Exception e) {
        super(message, e);
    }
}
