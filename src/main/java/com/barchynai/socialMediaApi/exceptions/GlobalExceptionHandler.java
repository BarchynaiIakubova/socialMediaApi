package com.barchynai.socialMediaApi.exceptions;

import com.barchynai.socialMediaApi.dto.responses.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongPasswordException.class)
    public ExceptionResponse handlerWrongPasswordException(WrongPasswordException e) {
        return new ExceptionResponse(
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException.class)
    public ExceptionResponse handlerNotFoundException(BadCredentialsException e) {
        return new ExceptionResponse(
                e.getClass().getSimpleName(),
                e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ExceptionResponse handlerNotFoundException(NotFoundException e) {
        return new ExceptionResponse(
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(AccessDeniedException.class)
    public ExceptionResponse handlerAccessDeniedException(AccessDeniedException e) {
        return new ExceptionResponse(
                e.getClass().getSimpleName(),
                e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ExceptionResponse handlerIllegalStateException(IllegalStateException e) {
        return new ExceptionResponse(
                e.getClass().getSimpleName(),
                e.getMessage());
    }
}
