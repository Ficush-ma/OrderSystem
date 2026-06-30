package com.orderSystem.common;

import com.orderSystem.entity.Result;
import com.orderSystem.exception.AmountException;
import com.orderSystem.exception.BaseException;
import com.orderSystem.exception.StockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public Result<Void> baseException(BaseException e){
        log.warn("基础异常,code: {}, msg: {}",e.getCode(),e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }
    @ExceptionHandler(AmountException.class)
    public Result<Void> amountException(AmountException e){
        log.warn("用户余额不足,code: {}, msg: {}",e.getCode(),e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }
    @ExceptionHandler(StockException.class)
    public Result<Void> stockException(StockException e){
        log.warn("库存不足,code: {}, msg: {}",e.getCode(),e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }
}
