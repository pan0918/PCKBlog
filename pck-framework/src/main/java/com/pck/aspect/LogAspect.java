package com.pck.aspect;


import com.alibaba.fastjson.JSON;
import com.pck.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.pck.annotation.SystemLog)")
    public void pt() {

    }

    @Around("pt()")
    // 定义通知方法
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed;
        try {
            handleBefore(joinPoint);
            proceed = joinPoint.proceed();
            handleAfter(proceed);
        } finally {
            // 结束后换行
            log.info("=======================end=======================" + System.lineSeparator());
        }

        return proceed;
    }

    private void handleAfter(Object proceed) {
        // 打印出参
        log.info("返回参数   : {}", JSON.toJSONString(proceed));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取方法注解对象
        SystemLog systemLog = getSystemLog(joinPoint);

        log.info("======================Start======================");
        // 打印请求 URL
        log.info("请求URL   : {}", request.getRequestURI());
        // 打印描述信息
        log.info("接口描述   : {}", systemLog.businessName());
        // 打印 Http method
        log.info("请求方式   : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("请求类名   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), ((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("访问IP    : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("传入参数   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }
}
