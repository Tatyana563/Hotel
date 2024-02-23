package com.hotel.exception_handler;

import com.hotel.exception_handler.exception.*;
import com.hotel.model.dto.response.error.registration.RegistrationErrorPayload;
import com.hotel.model.dto.response.error.registration.RegistrationErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
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
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(
            ExpiredJwtException ex) {

        List<String> errors = Collections.singletonList(ex.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(606, LocalDateTime.now(), errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> roleNotFoundException(
            RoleNotFoundException ex) {

        List<String> errors = Collections.singletonList(ex.getResourceId());

        ErrorMessage errorMessage = new ErrorMessage(608, LocalDateTime.now(), errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyCreatedException.class)
    public ResponseEntity<RegistrationErrorResponse> userAlreadyCreatedExceptionException(
            UserAlreadyCreatedException ex) {
        RegistrationErrorResponse response = RegistrationErrorResponse.of(ex.isEnabled());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenResetPasswordException.class)
    public ResponseEntity<Object> handleInvalidTokenResetPasswordException(
            InvalidTokenResetPasswordException ex) {

        ErrorMessage errorMessage = new ErrorMessage(609, LocalDateTime.now(), Collections.singletonList(ex.getResourceId()));

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {

        ErrorMessage errorMessage = new ErrorMessage(609, LocalDateTime.now(), Collections.singletonList(ex.getMessage()));

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

}