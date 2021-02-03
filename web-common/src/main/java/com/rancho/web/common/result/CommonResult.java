package com.rancho.web.common.result;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用返回对象
 */
@Data
@ApiModel(description = "响应对象")
public class CommonResult<T>{

    @ApiModelProperty(value = "响应码")
    public String code;

    @ApiModelProperty(value = "响应消息")
    public String message;

    @ApiModelProperty(value = "响应数据")
    public T data;

    public CommonResult(String code,String message,T data){
        this.code=code;
        this.message=message;
        this.data=data;
    }

    public CommonResult(String code,String message){
        this.code=code;
        this.message=message;
    }


    public static CommonResult ok(){
        return new CommonResult(ResultCode.OK.getCode(),ResultCode.OK.getMessage());
    }

    public static <T> CommonResult ok(T data){
        return new CommonResult(ResultCode.OK.getCode(),ResultCode.OK.getMessage(),data);
    }

    public static CommonResult badRequest(String message){
        return new CommonResult(ResultCode.BAD_REQUEST.getCode(),message);
    }

    public static CommonResult unauthorized(){
        return new CommonResult(ResultCode.UNAUTHORIZED.getCode(),ResultCode.UNAUTHORIZED.getMessage());
    }

    public static CommonResult forbidden(){
        return new CommonResult(ResultCode.FORBIDDEN.getCode(),ResultCode.FORBIDDEN.getMessage());
    }

    public static CommonResult error(){
        return new CommonResult(ResultCode.INTERNAL_SERVER_ERROR.getCode(),ResultCode.INTERNAL_SERVER_ERROR.getMessage());
    }
}
