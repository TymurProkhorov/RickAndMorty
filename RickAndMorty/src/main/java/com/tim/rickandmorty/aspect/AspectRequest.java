package com.tim.rickandmorty.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class AspectRequest {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void allResources() {
    }
    @Before("allResources()")
    public void requestLog(JoinPoint joinPoint) {
        LogManager.getLogger(joinPoint.getSignature().getDeclaringTypeName()).info("get log request");
        String log = joinPoint.getSignature().getName() + " >>>";
        for (Object arg : joinPoint.getArgs()) {
            log += "\n   ARG: " + arg;
        }
        LogManager.getLogger(joinPoint.getSignature().getDeclaringTypeName()).info(log);
    }
}
//    @Around("execution(* com.tim.rickandmorty.controller..*.*(..))")
//    public Object logApiCall(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        log.debug("{} {} from {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr());
//        return request;
//    }


