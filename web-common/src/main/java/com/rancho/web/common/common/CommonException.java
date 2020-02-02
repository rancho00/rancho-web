package com.rancho.web.common.common;

public class CommonException extends RuntimeException{

    private CommonException(){
    }

    public CommonException(String message){
        super(message);
    }
}
