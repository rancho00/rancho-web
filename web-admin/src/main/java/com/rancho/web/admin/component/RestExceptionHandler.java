package com.rancho.web.admin.component;

import com.rancho.web.common.common.CommonException;
import com.rancho.web.common.common.NotLoginException;
import com.rancho.web.common.result.CommonResult;
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
        return ResponseEntity.badRequest().body(new CommonResult().badRequest(errorMsg));
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
        return ResponseEntity.badRequest().body(new CommonResult().badRequest(errorMsg));
    }


//    /**
//     * 处理CustomException异常
//     * @param commonException
//     * @return
//     */
//    @ExceptionHandler(CommonException.class)
//    public ResponseEntity<CommonResult> handleCustomException(CommonException commonException){
//        return ResponseEntity.badRequest().body(new CommonResult().badRequest(commonException.getMessage()));
//    }
//
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
