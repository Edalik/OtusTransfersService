package ru.otus.transfers.service.exceptions_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.transfers.service.exceptions_handling.dtos.ErrorDto;
import ru.otus.transfers.service.exceptions_handling.dtos.ValidationErrorDto;
import ru.otus.transfers.service.exceptions_handling.dtos.ValidationFieldErrorDto;
import ru.otus.transfers.service.exceptions_handling.exceptions.ResourceNotFoundException;
import ru.otus.transfers.service.exceptions_handling.exceptions.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> catchResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ErrorDto("RESOURCE_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ValidationErrorDto> catchValidationException(ValidationException e) {
        return new ResponseEntity<>(
                new ValidationErrorDto(
                        e.getCode(),
                        e.getMessage(),
                        e.getErrors().stream().map(ve -> new ValidationFieldErrorDto(ve.getField(), ve.getMessage())).toList()
                ),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

}