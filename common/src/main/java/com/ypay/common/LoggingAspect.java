package com.ypay.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import javax.validation.constraints.NotNull;

@Aspect
@Component
public class LoggingAspect {

    private final LoggingProducer loggingProducer;


    public LoggingAspect(LoggingProducer loggingProducer) {
        this.loggingProducer = loggingProducer;
    }


    @Before("execution(* com.ypay.*.adapter.in.web.*.*(..))")
    public void beforeMethodExecution(@NotNull JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        loggingProducer.sendMessage("logging", "Before executing method: " + methodName);
    }

}
