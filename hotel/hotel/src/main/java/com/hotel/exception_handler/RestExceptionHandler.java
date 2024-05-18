package com.hotel.exception_handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.hotel.exception_handler.exception.*;
import com.hotel.model.dto.response.ErrorTypeEnum;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> handleRegistrationException(
            InvalidTokenException ex) {

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

        List<String> errors = Collections.singletonList(ex.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(608, LocalDateTime.now(), errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    //    @ExceptionHandler(UserAlreadyCreatedException.class)
//    public ResponseEntity<RegistrationErrorResponse> userAlreadyCreatedExceptionException(
//            UserAlreadyCreatedException ex) {
//        RegistrationErrorResponse response = RegistrationErrorResponse.of(ex.isEnabled());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
    @ExceptionHandler({UserAlreadyCreatedException.class, UserNotFoundException.class})
    public ResponseEntity<Object> userAlreadyCreatedExceptionException(
            RuntimeException ex) {
        ErrorMessage errorMessage = new ErrorMessage(609, LocalDateTime.now(), Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) cause);
        } else {
            return DeserializationErrorResponse.builder()
                    .errorTypeEnum(ErrorTypeEnum.DESERIALIZATION_ERROR)
//                    .targetType()
//                    .path()
                    .message(ex.getMessage())
                    .build();
        }
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleInvalidFormatException(InvalidFormatException ex) {
        return DeserializationErrorResponse.builder()
                .errorTypeEnum(ErrorTypeEnum.DESERIALIZATION_ERROR)
                .path(ex.getPathReference())
                .targetType(ex.getTargetType().getName())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<ValidationErrorDTO> validationErrorsDTO = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> objectErrors = ex.getBindingResult().getGlobalErrors();

        fieldErrors.stream()
                .map(fieldError -> {
                    String fieldName = fieldError.getField();

                    Object rejectedValue = fieldError.getRejectedValue();
//TODO: payload in validation
//                    Optional<List<String>> acceptableValues = Optional.ofNullable(fieldError.getCodes())
//                            .map(codes -> Stream.of(codes).collect(Collectors.toList()));
//TODO: add path
                    String message = fieldError.getDefaultMessage();

                    return ValidationErrorDTO.builder()
                            .fieldName(fieldName)
                            .rejectedValue(rejectedValue)
                            .message(message)
                            .build();
                })
                .forEach(validationErrorsDTO::add);

        objectErrors.stream()
                .map(objectError -> {


                    Object rejectedValue = ex.getBindingResult().getTarget();

                    String message = objectError.getDefaultMessage();

                    return ValidationErrorDTO.builder()
                            .rejectedValue(rejectedValue)
                            .message(message)
                            .build();
                })
                .forEach(validationErrorsDTO::add);

        return ValidationErrorResponse.builder()
                .errorTypeEnum(ErrorTypeEnum.VALIDATION_ERROR)
                .validationErrors(validationErrorsDTO)
                .message(ex.getMessage())
                .build();
    }
}


