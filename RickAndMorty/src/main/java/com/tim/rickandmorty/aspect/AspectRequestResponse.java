package com.tim.rickandmorty.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
@Slf4j
public class AspectRequestResponse {

    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

    public AspectRequestResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }
    @Around("execution(* com.tim.rickandmorty.controller..*.*(..))")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {

            Object result = joinPoint.proceed();
        if (log.isInfoEnabled()) {
            log.info("request={}, response={}, traceUID={}",
                    httpServletRequest.getRequestURL(),
                    result,
                    httpServletResponse.getHeader("traceUID"));

        }
            return result;
    }
}


