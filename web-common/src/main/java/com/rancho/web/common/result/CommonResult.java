package com.rancho.web.common.result;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用返回对象
 */
@Data
@ApiModel(description = "响应对象")
public class CommonResult<T> {

    @ApiModelProperty(value = "响应码")
    private String code;

    @ApiModelProperty(value = "响应消息")
    private String message;

    @ApiModelProperty(value = "响应数据")
    private T data;

    public CommonResult(){

    }

    public CommonResult(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public CommonResult ok(){
        this.code = ResultCode.OK.getCode();
        this.message = ResultCode.OK.getMessage();
        return this;
    }

    public CommonResult ok(T data){
        this.code = ResultCode.OK.getCode();
        this.message = ResultCode.OK.getMessage();
        this.data=data;
        return this;
    }

    public CommonResult badRequest(String message){
        this.code = ResultCode.BAD_REQUEST.getCode();
        this.message = message;
        return this;
    }

    public CommonResult unauthorized(String message){
        this.code = ResultCode.UNAUTHORIZED.getCode();
        this.message = message;
        return this;
    }

    public CommonResult forbidden(String message){
        this.code = ResultCode.FORBIDDEN.getCode();
        this.message = message;
        return this;
    }
}
