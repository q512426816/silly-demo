package com.iqiny.silly.demo.common.web;


import com.iqiny.silly.common.exception.SillyException;
import com.iqiny.silly.demo.common.sc.SillyR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 */
@RestControllerAdvice(annotations = RestController.class)
public class MyRestExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(DuplicateKeyException.class)
    public SillyR handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return SillyR.error("数据库中已存在该记录" );
    }

    /**
     * 处理傻瓜异常
     */
    @ExceptionHandler(SillyException.class)
    public SillyR handleRuntimeException(SillyException e) {
        logger.warn(e.getMessage(), e);
        return SillyR.error(e.getMessage());
    }

    /**
     * 其余异常处理
     */
    @ExceptionHandler(Exception.class)
    public SillyR handleAllException(Exception e) {
        logger.error(e.getMessage(), e);
        return SillyR.error("服务器异常！请联系管理员！" + e.getMessage());
    }
}
