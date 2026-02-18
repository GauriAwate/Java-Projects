package com.example.bookstoreweb.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Before("execution(* com.example.bookstore.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Before: {}", joinPoint.getSignature());
    }

    @AfterReturning("execution(* com.example.bookstore.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        log.info("After: {}", joinPoint.getSignature());
    }
}
