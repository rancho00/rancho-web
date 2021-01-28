package com.rancho.web.admin.component;

import com.rancho.web.common.common.BadRequestException;
import com.rancho.web.common.common.UnAuthorizedException;
import com.rancho.web.common.result.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * 全局异常<br>
     *     与security exception有冲突，不会执行RestAccessDeniedHandler和RestAuthenticationEntryPoint
     * @param exception
     * @return
     */
//    @ExceptionHandler
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String handleException(Exception exception) {
//        log.error(exception.getMessage());
//        return exception.getMessage();
//    }

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
        return ResponseEntity.badRequest().body(CommonResult.badRequest(errorMsg));
    }


    /**
     * 处理badRequestException异常
     * @param badRequestException
     * @return
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResult> handleCustomException(BadRequestException badRequestException){
        return ResponseEntity.badRequest().body(new CommonResult(badRequestException.getResultCode().getCode(),badRequestException.getResultCode().getMessage()));
    }

    /**
     * 处理unAuthorizedException异常
     * @param unAuthorizedException
     * @return
     */
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<CommonResult> handleUnAuthorizedException(UnAuthorizedException unAuthorizedException){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CommonResult(unAuthorizedException.getResultCode().getCode(),unAuthorizedException.getResultCode().getMessage()));
    }

//    /**
//     * 处理NotLoginException异常
//     * @param notLoginException
//     * @return
//     */
//    @ExceptionHandler(NotLoginException.class)
//    public ResponseEntity<CommonResult> handleNotLoginException(NotLoginException notLoginException){
//        return ResponseEntity.badRequest().body(new CommonResult().badRequest(""));
//    }

}
