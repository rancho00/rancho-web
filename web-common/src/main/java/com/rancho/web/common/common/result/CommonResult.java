package com.rancho.web.common.common.result;


import com.rancho.web.common.result.IErrorCode;
import com.rancho.web.common.result.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 通用返回对象
 */
@ApiModel(description = "响应对象")
public class CommonResult<T> {

    @ApiModelProperty(value = "响应码")
    private long code;

    @ApiModelProperty(value = "响应消息")
    private String message;

    @ApiModelProperty(value = "响应数据")
    private T data;

    protected CommonResult() {
    }

    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(com.rancho.web.common.result.ResultCode.SUCCESS.getCode(), com.rancho.web.common.result.ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     * @param message 消息
     */
    public static <T> CommonResult<T> success(String message) {
        return new CommonResult<T>(com.rancho.web.common.result.ResultCode.SUCCESS.getCode(), message, null);
    }


    /**
     * 成功返回结果
     *
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(com.rancho.web.common.result.ResultCode.SUCCESS.getCode(), com.rancho.web.common.result.ResultCode.SUCCESS.getMessage(), null);
    }


    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> CommonResult<T> success(String message,T data) {
        return new CommonResult<T>(com.rancho.web.common.result.ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(com.rancho.web.common.result.ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(com.rancho.web.common.result.ResultCode.FAILED);
    }


    /**
     * 未登陆回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(com.rancho.web.common.result.ResultCode.UNAUTHORIZED.getCode(), com.rancho.web.common.result.ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(com.rancho.web.common.result.ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
