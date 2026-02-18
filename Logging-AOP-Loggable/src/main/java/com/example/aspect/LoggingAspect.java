package com.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(com.example.annotation.Loggable)")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();

        logger.trace("Entering {}.{} with arguments: {}", className, methodName, Arrays.toString(args));

        try {
            Object result = joinPoint.proceed();
            logger.info("Exiting {}.{} with result: {}", className, methodName, result);
            return result;
        } catch (Throwable e) {
            logger.error("Exception in {}.{}: {}", className, methodName, e.getMessage(), e);
            throw e;
        }
    }
}