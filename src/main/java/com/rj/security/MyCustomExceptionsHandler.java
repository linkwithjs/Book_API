package com.rj.security;

//import java.net.http.HttpHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.rj.security.dto.ExceptionMessageDTO;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class MyCustomExceptionsHandler {
    @ExceptionHandler(value = { UsernameNotFoundException.class })
    public ResponseEntity<Object> handleUsernameNotFoundException(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI().toString();
        ExceptionMessageDTO exceptionMessageDTO = new ExceptionMessageDTO(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                ex.getMessage(), requestUri);
        return new ResponseEntity<>(exceptionMessageDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ResponseStatusException.class})
    public ResponseEntity<Object> handleResponseStatusException(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        ExceptionMessageDTO exceptionMessageDTO = new ExceptionMessageDTO(HttpStatus.BAD_REQUEST.toString(),ex.getMessage(), requestUri);
        return new ResponseEntity<>(exceptionMessageDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
