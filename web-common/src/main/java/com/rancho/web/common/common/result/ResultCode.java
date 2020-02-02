package com.rancho.web.common.common.result;

import com.rancho.web.common.result.IErrorCode;

/**
 * 枚举了一些常用API操作码
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    //自定义
    UNAUTH(999,"未认证"),
    AUTHING(998,"认证中"),
    AUTHSUCCESS(997,"认证完成"),
    ;

    private long code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
