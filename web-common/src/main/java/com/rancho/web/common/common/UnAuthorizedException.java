package com.rancho.web.common.common;

import com.rancho.web.common.result.ResultCode;

public class UnAuthorizedException extends RuntimeException{

    private ResultCode resultCode;

    private UnAuthorizedException(){
    }

    public UnAuthorizedException(ResultCode resultCode){
        this.resultCode=resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public UnAuthorizedException message(String message){
        this.getResultCode().setMessage(message);
        return this;
    }

}
