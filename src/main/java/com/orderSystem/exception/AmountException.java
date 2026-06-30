package com.orderSystem.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmountException extends RuntimeException {
    private final int code;
    private final String message;
    public AmountException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
