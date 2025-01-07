package ru.otus.transfers.service.exceptions_handling.exceptions;

import lombok.Getter;
import ru.otus.transfers.service.exceptions_handling.dtos.ValidationFieldError;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

    private final String code;

    private final List<ValidationFieldError> errors;

    public ValidationException(String code, String message, List<ValidationFieldError> errors) {
        super(message);
        this.code = code;
        this.errors = errors;
    }

}