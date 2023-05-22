package com.rj.security;


import com.rj.security.exception.EmailAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.rj.security.dto.ExceptionMessageDTO;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class MyCustomExceptionsHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { UsernameNotFoundException.class })
    public ResponseEntity<Object> handleUsernameNotFoundException(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI().toString();
        ExceptionMessageDTO exceptionMessageDTO = new ExceptionMessageDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(), requestUri);
        return new ResponseEntity<>(exceptionMessageDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = { EmailAlreadyExistsException.class })
    public ResponseEntity<Object> handleEmailAlreadyExistsException(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI().toString();
        ExceptionMessageDTO exceptionMessageDTO = new ExceptionMessageDTO(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(), requestUri);
        return new ResponseEntity<>(exceptionMessageDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {ResponseStatusException.class})
    public ResponseEntity<Object> handleResponseStatusException(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        ExceptionMessageDTO exceptionMessageDTO = new ExceptionMessageDTO(HttpStatus.BAD_REQUEST.value(),ex.getMessage(), requestUri);
        return new ResponseEntity<>(exceptionMessageDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        ExceptionMessageDTO exceptionMessageDTO = new ExceptionMessageDTO(HttpStatus.BAD_REQUEST.value(),ex.getMessage(), requestUri);
        return new ResponseEntity<>(exceptionMessageDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ExceptionMessageDTO> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ExceptionMessageDTO errorDetails = new ExceptionMessageDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public final ResponseEntity<ExceptionMessageDTO> handleInsufficientAuthenticationException(InsufficientAuthenticationException ex, WebRequest request) {
        ExceptionMessageDTO errorDetails = new ExceptionMessageDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }



}
