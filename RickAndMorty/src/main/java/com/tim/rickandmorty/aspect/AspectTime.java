package com.tim.rickandmorty.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AspectTime {

    @Around("execution(* com.tim.rickandmorty.controller..*.*(..))")
    public Object controller(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();                 //снимаем текущее время, до старта
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start; //снимаем время после выполнения метода
        log.info(joinPoint.getSignature() + "Time={" + executionTime + "} ms");
        return proceed;
    }
    }
