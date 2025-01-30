package ru.otus.transfers.service.exceptions_handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.transfers.service.exceptions_handling.dtos.ErrorDto;
import ru.otus.transfers.service.exceptions_handling.dtos.ValidationErrorDto;
import ru.otus.transfers.service.exceptions_handling.dtos.ValidationFieldErrorDto;
import ru.otus.transfers.service.exceptions_handling.exceptions.BusinessLogicException;
import ru.otus.transfers.service.exceptions_handling.exceptions.ResourceNotFoundException;
import ru.otus.transfers.service.exceptions_handling.exceptions.ValidationException;

import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDto> catchException(Exception e) {
        UUID uuid = UUID.randomUUID();
        log.error(String.format("Unexpected exception with guid: %s error message: %s", uuid, e.getMessage()), e);

        return new ResponseEntity<>(
                new ErrorDto("UNEXPECTED_EXCEPTION", String.format("Unexpected exception with guid: %s", uuid)),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(value = BusinessLogicException.class)
    public ResponseEntity<ErrorDto> catchBusinessLogicException(BusinessLogicException e) {
        return new ResponseEntity<>(
                new ErrorDto("BUSINESS_LOGIC_EXCEPTION", e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

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
                        e.getErrors()
                                .stream()
                                .map(ve -> new ValidationFieldErrorDto(ve.getField(), ve.getMessage()))
                                .toList()
                ),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

}