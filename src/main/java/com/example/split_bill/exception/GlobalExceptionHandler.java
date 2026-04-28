package com.example.split_bill.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(
            NotFoundException ex,
            HttpServletRequest request
    ) {
        return new ErrorResponse(
                LocalDateTime.now(),
                404,
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request
    ) {
        return new ErrorResponse(
                LocalDateTime.now(),
                400,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneral(
            Exception ex,
            HttpServletRequest request
    ) {
        return new ErrorResponse(
                LocalDateTime.now(),
                500,
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}