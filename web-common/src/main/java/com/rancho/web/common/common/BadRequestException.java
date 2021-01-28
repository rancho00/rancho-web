package com.rancho.web.common.common;

import com.rancho.web.common.result.ResultCode;

public class BadRequestException extends RuntimeException{

    private ResultCode resultCode;

    private BadRequestException(){
    }

    public BadRequestException(ResultCode resultCode){
        this.resultCode=resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public BadRequestException message(String message){
        this.getResultCode().setMessage(message);
        return this;
    }

}
