package com.rj.security.exception;

public class EmailAlreadyExistsException extends Exception{
    private static final long serialVersionUID = -3332292346834265371L;

    public EmailAlreadyExistsException(String message){
        super(message);
    }

}
