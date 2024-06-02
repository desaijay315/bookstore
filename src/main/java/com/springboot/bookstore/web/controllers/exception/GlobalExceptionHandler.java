package com.springboot.bookstore.web.controllers.exception;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.springboot.bookstore.domain.BookNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final URI NOT_FOUND_TYPE = URI.create("https://api.bookstore.com/errors/not-found");
    private static final URI ISE_FOUND_TYPE = URI.create("https://api.bookstore.com/errors/server-error");
    private static final URI BAD_REQUEST_TYPE = URI.create("https://api.bookstore.com/errors/bad-request");
    private static final URI VALIDATION_ERROR_TYPE = URI.create("https://api.bookstore.com/errors/validation-error");
    private static final String SERVICE_NAME = "book-service";

    @ExceptionHandler(Exception.class)
    ProblemDetail handleUnhandledException(Exception e) {
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, ISE_FOUND_TYPE, "Internal Server Error", e.getMessage());
        problemDetail.setProperty("error_category", "Generic");
        return problemDetail;
    }

    @ExceptionHandler(BookNotFoundException.class)
    ProblemDetail handleBookNotFoundException(BookNotFoundException e) {
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.NOT_FOUND, NOT_FOUND_TYPE, "Book Not Found", e.getMessage());
        problemDetail.setProperty("error_category", "ResourceNotFound");
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ProblemDetail handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String error = String.format("The parameter '%s' with value '%s' could not be converted to type '%s'", e.getName(), e.getValue(), e.getRequiredType().getSimpleName());
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.BAD_REQUEST, BAD_REQUEST_TYPE, "Type Mismatch Error", error);
        problemDetail.setProperty("error_category", "TypeMismatch");
        return problemDetail;
    }

    private ProblemDetail createProblemDetail(HttpStatus status, URI type, String title, String detail) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        problemDetail.setType(type);
        problemDetail.setProperty("service", SERVICE_NAME);
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}