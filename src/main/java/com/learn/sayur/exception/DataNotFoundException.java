package com.learn.sayur.exception;

import lombok.Data;

@Data
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}