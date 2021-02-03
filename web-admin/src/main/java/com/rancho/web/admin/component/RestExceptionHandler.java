package com.rancho.web.admin.component;

import com.rancho.web.common.common.BadRequestException;
import com.rancho.web.common.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * 全局异常<br>
     *     与security exception有冲突，不会执行RestAccessDeniedHandler和RestAuthenticationEntryPoint
     *     直接捕获AccessDeniedException，AuthenticationException异常
     * @param e
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<CommonResult> handleException(Exception e) {
        log.error("全局异常-------->：{}",e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonResult.error());
    }

    /**
     * 处理不带任何注解的参数绑定校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonResult> handleBingException(BindException e) {
        String errorMsg = e.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> ((FieldError)objectError).getField() + ((FieldError)objectError).getDefaultMessage())
                .collect(Collectors.joining(","));
        //"errorMsg": "name不能为空,age最小不能小于18"
        log.error("参数绑定异常-------->：{}",errorMsg);
        return ResponseEntity.badRequest().body(CommonResult.badRequest(errorMsg));
    }

    /**
     * 处理 @RequestBody参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResult> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> ((FieldError)objectError).getDefaultMessage())
                .collect(Collectors.joining(","));
        log.error("RequestBody参数绑定异常-------->：{}",errorMsg);
        return ResponseEntity.badRequest().body(CommonResult.badRequest(errorMsg));
    }

    /**
     * 处理badRequestException异常
     * @param badRequestException
     * @return
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResult> handleBadRequestException(BadRequestException badRequestException){
        log.error("错误请求异常-------->：{}",badRequestException.getResultCode().getMessage());
        return ResponseEntity.badRequest().body(new CommonResult(badRequestException.getResultCode().getCode(),badRequestException.getResultCode().getMessage()));
    }

    /**
     * 处理accessDeniedException异常
     * @param accessDeniedException
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAccessDeniedException(AccessDeniedException accessDeniedException){
        log.error("没有权限异常-------->：{}",accessDeniedException.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(CommonResult.forbidden());
    }

    /**
     * 处理authenticationException异常
     * @param authenticationException
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity handleAuthenticationException(AuthenticationException authenticationException){
        log.error("没有认证异常-------->：{}",authenticationException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResult.unauthorized());
    }

}
