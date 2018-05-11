package com.tianyi.web;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lingqingwan on 12/28/15
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    public static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public Map<String, Object> handleUnauthorizedException(HttpServletRequest request, Exception ex) {
        LOGGER.error("error", ex);

        Map<String, Object> results = new HashMap<>();
        results.put("err_code", 405);
        results.put("err_msg", ex.getMessage());
        ex.printStackTrace();
        return results;
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, Object> handleException(HttpServletRequest request, Exception ex) {
        LOGGER.error("error", ex);

        Map<String, Object> results = new HashMap<>();
        results.put("err_code", 500);
        results.put("err_msg", ex.getMessage());
        ex.printStackTrace();
        return results;
    }



}
