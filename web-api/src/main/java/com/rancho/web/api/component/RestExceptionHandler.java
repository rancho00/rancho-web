package com.rancho.web.api.component;

import com.rancho.web.common.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
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
     * 处理参数绑定异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public String handleBingException(BindException e) {
        String errorMsg = e.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> ((FieldError)objectError).getDefaultMessage())
                .collect(Collectors.joining(","));
        log.error(errorMsg);
        return errorMsg;
    }

    /**
     * 处理列表参数校验异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public String handleConstraintViolationException(ConstraintViolationException e) {
        String errorMsg = e.getConstraintViolations().stream().filter(Objects::nonNull)
                .map(cv -> cv == null ? "null" : cv.getMessage())
                .collect(Collectors.joining(", "));
        log.error(errorMsg);
        return errorMsg;
    }

    /**
     * 处理bean参数校验异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> ((FieldError)objectError).getDefaultMessage())
                .collect(Collectors.joining(","));
        log.error(errorMsg);
        return errorMsg;
    }


    /**
     * 处理ServerException异常
     * @param serverException
     * @return
     */
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(ServerException.class)
//    @ResponseBody
//    public String handleExistException(ServerException serverException) {
//        log.error(serverException.getMessage());
//        return serverException.getMessage();
//    }
//
//    /**
//     * 处理CustomException异常
//     * @param customException
//     * @return
//     */
//    @ExceptionHandler(CustomException.class)
//    @ResponseBody
//    public CommonResult handleCustomException(CustomException customException) {
//        log.error(customException.getMessage());
//        return CommonResult.custom(customException.getiErrorCode());
//    }

}
