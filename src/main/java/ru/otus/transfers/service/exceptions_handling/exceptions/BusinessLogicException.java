package ru.otus.transfers.service.exceptions_handling.exceptions;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {

    private final String code;

    public BusinessLogicException(String message, String code) {
        super(message);
        this.code = code;
    }

}