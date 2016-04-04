package com.twodfire.aspect;

import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * User: edagarli
 * Email: lizhi@edagarli.com
 * Date: 2015/6/5
 * Time: 14:34
 * Desc: 定义环绕通知切面
 */

@Service
@Aspect
public class MethodAspect {

    private static final Logger logger = LoggerFactory.getLogger(MethodAspect.class);

    @Around("@annotation(com.twodfire.annotation.MethodAnnotation)")
    public Object validator(ProceedingJoinPoint joinPoint) throws Throwable {
        Gson gson=new Gson();
        String cName = joinPoint.getTarget().getClass().getName();
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        Object[]args=getArgs(joinPoint);
        long sTime=System.nanoTime();
        Object result=  joinPoint.proceed();
        long eTime=System.nanoTime();
        StringBuffer buffer=new StringBuffer();
        buffer.append(cName).
                append(".").
                append(method.getName()).
                append("  ").
                append(gson.toJson(args)).
                append("  result ").
                append(gson.toJson(result)).
                append("  consumeTime: ").
                append((eTime-sTime)/1000000).
                append(" ms");
        logger.info(buffer.toString());
        System.out.println(buffer.append("监控信息").toString());
        return null;
    }

    public Object[] getArgs(ProceedingJoinPoint joinPoint){
        Object[] arguments = joinPoint.getArgs();
        return arguments;
    }
}
