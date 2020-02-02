package com.rancho.web.common.common.common;

public class NotLoginException extends RuntimeException{

    public NotLoginException(){
    }

    public NotLoginException(String message){
        super(message);
    }
}
