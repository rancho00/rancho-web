package com.rancho.web.common.result;

/**
 * 枚举了一些常用API操作码
 */
public enum ResultCode implements IErrorCode {
    OK(200,"OK", "成功"),
    BAD_REQUEST(400,"BAD_REQUEST", "错误请求"),
    UNAUTHORIZED(401,"UNAUTHORIZED", "未认证"),
    FORBIDDEN(403,"FORBIDDEN", "未授权"),
    NOT_FOUND(404,"NOT_FOUND", "资源不存在"),
    INTERNAL_SERVER_ERROR(500,"INTERNAL_SERVER_ERROR", "服务器异常"),
    ;

    private String code;
    private String message;

    ResultCode(int status,String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
