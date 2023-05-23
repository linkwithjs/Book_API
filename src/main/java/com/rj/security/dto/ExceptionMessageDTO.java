package com.rj.security.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionMessageDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private String errorCode;
    private String errorMessage;
    private String path;
    private LocalDateTime timestamp;

    public ExceptionMessageDTO() {
        timestamp = LocalDateTime.now();
    }

    public ExceptionMessageDTO(String errorCode, String errorMessage, String path) {
        this();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.path = path;
    }
}
