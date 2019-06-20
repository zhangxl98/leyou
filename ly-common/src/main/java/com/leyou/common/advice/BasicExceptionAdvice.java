package com.leyou.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/20/19 6:51 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description SpringMVC 异常拦截器
 */
@ControllerAdvice
@Slf4j
public class BasicExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException e) {
        // 返回状态码 400，从异常中提取提示信息
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
