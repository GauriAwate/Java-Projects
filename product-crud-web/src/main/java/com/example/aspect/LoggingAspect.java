package com.example.aspect;

import com.example.annotation.Loggable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(com.example.annotation.Loggable)")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        logger.debug("Entering method: {} with arguments: {}", methodName, args);
        try {
            Object result = joinPoint.proceed();
            logger.debug("Exiting method: {} with return value: {}", methodName, result);
            return result;
        } catch (Throwable t) {
            logger.error("Exception in method: {} - {}", methodName, t.getMessage(), t);
            throw t;
        }
    }
}