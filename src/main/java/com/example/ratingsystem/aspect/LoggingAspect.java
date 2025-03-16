package com.example.ratingsystem.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@within(Loggable)")
    public void loggablePointcut() {}

    @Before("loggablePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        String args = String.join(", ", Arrays.stream(joinPoint.getArgs()).map(Objects::toString).toList());
        logger.info("{} was called with args {}", joinPoint.getSignature(), args);
    }

    @Around("loggablePointcut()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        var result = joinPoint.proceed();
        long execTime = System.currentTimeMillis() - startTime;
        logger.info("{} execution time: {} ms", getMethodName(joinPoint), execTime);
        return result;
    }

    @AfterThrowing(value = "loggablePointcut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        logger.error("Error during execution of {}: {}", getMethodName(joinPoint), ex.getMessage());
    }

    private String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }
}