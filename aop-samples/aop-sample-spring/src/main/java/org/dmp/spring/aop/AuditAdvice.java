package org.dmp.spring.aop;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AuditAdvice {

    private static final Logger logger = Logger.getLogger(AuditAdvice.class);
    
    @Pointcut("execution(* org.dmp.spring.rest.*RestService.*RestCall(..))")
    public void restCalls() { }
    
    @Before("restCalls()")
    public void doBefore(JoinPoint joinpoint) {
        logger.info("Auditor:  Invoking " + joinpoint.getSignature() + "; Arguments " + Arrays.toString(joinpoint.getArgs()));
    }
    
    @After("restCalls()")
    public void doAfter(JoinPoint joinpoint) {
        logger.info("Auditor:  Returning from method: " + joinpoint.getSignature() + ";");
    }
    
    @Around("restCalls()")
    public Object helloAppender(ProceedingJoinPoint joinpoint) throws Throwable {
        Object returnObj = joinpoint.proceed();
        if (returnObj instanceof String) {
            returnObj = new String("Rest Respose:  " + returnObj);
        }
        return returnObj;
    }

}
