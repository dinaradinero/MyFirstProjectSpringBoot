package org.example.myfirstproject.controller;

import jakarta.validation.ConstraintViolationException;
import org.example.myfirstproject.service.exception.AlreadyExistException;
import org.example.myfirstproject.service.exception.IllegalArgumentException;
import org.example.myfirstproject.service.exception.NotFoundException;
import org.example.myfirstproject.service.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<String> handlerAlreadyExistException(AlreadyExistException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handlerNotFoundException(NotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handlerValidationException(ValidationException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<String> handlerIllegalArgumentException(IllegalArgumentException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handlerNullPointerException(NullPointerException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handlerConstraintViolationException(ConstraintViolationException e){

        StringBuilder responseMessage = new StringBuilder();

        e.getConstraintViolations().forEach(
                constraintViolation -> {
                    String currentErrorMessage = constraintViolation.getMessage();
                    responseMessage.append(currentErrorMessage);
                    responseMessage.append("\n");
                }
        );

        return new ResponseEntity<>(responseMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){

        StringBuilder responseMessage = new StringBuilder();

        e.getBindingResult().getAllErrors().forEach(
                objectError -> {
                    String fieldName = ((FieldError) objectError).getField();
                    String currentErrorMessage = objectError.getDefaultMessage();
                    responseMessage.append(fieldName + " : " + currentErrorMessage);
                    responseMessage.append("\n");
                }
        );

        return new ResponseEntity<>(responseMessage.toString(), HttpStatus.BAD_REQUEST);
    }
}
