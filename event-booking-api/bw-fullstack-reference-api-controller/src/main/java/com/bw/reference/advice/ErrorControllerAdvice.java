package com.bw.reference.advice;

import com.bw.commons.apiclient.ApiResponse;
import com.bw.reference.exception.ErrorResponse;
import com.bw.reference.exception.InvalidTokenException;
import com.bw.reference.exception.NotFoundException;
import com.bw.reference.exception.RecordInUseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since August, 2020
 **/
@ControllerAdvice
public class ErrorControllerAdvice {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MessageSource messageSource;

    public ErrorControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handle(NotFoundException e) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(404);
        apiResponse.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(RecordInUseException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<?> handle(RecordInUseException e) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.PRECONDITION_FAILED.value());
        apiResponse.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(apiResponse);
    }

    @ExceptionHandler(ErrorResponse.class)
    public ResponseEntity<?> handle(ErrorResponse e) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(messageSource.getMessage(e.getMessageKey(), e.getArgs(), e.getMessageKey(), LocaleContextHolder.getLocale()));
        apiResponse.setCode(e.getCode());

        return ResponseEntity.status(e.getCode()).body(apiResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handle(IllegalArgumentException e) {
        logger.error(e.getMessage(), e);

        ApiResponse<String> apiResponse = new ApiResponse();
        if (e.getCause() != null) {
            apiResponse.setMessage(e.getCause().getMessage());
        } else {
            apiResponse.setMessage(e.getMessage());
        }
        apiResponse.setCode(400);
        apiResponse.setMessage(messageSource.getMessage(apiResponse.getMessage(), null, apiResponse.getMessage(), LocaleContextHolder.getLocale()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handle(InvalidTokenException e) {
        logger.error(e.getMessage(), e);

        ApiResponse<String> apiResponse = new ApiResponse();
        if (e.getCause() != null) {
            apiResponse.setMessage(e.getCause().getMessage());
        } else {
            apiResponse.setMessage(e.getMessage());
        }
        apiResponse.setCode(HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex,
            WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getPropertyPath() + " : " + violation.getMessage());
        }
        ApiResponse<List<String>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(400);
        apiResponse.setMessage(errors.size() > 0 ? errors.get(0) : "Invalid request");
        apiResponse.setData(errors);
        return new ResponseEntity<Object>(apiResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
