package com.example.medebv.exception;

public class AirtableApiException extends RuntimeException{
    public AirtableApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
