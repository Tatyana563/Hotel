package com.hotel.exception_handler;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorMessage errorMessage = new ErrorMessage(601, LocalDateTime.now(), errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex) {

        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
//TODO: ErrorMessage instead of body
        ErrorMessage errorMessage = new ErrorMessage(602, LocalDateTime.now(), errors);

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HotelNotFoundException.class, RoomNotFoundException.class})
    public ResponseEntity<Object> handleHotelNotFoundException(
            AbstractNotFoundException ex) {

        List<String> errors = Collections.singletonList(ex.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(603, LocalDateTime.now(), errors);

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<Object> handleConversionFailedException(
            ConversionFailedException ex) {

        List<String> errors = Collections.singletonList(ex.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(604, LocalDateTime.now(), errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TokenExpirationException.class, RegistrationNotFoundException.class})
    public ResponseEntity<Object> handleRegistrationException(
            AbstractNotFoundException ex) {

        List<String> errors = Collections.singletonList(ex.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(605, LocalDateTime.now(), errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}