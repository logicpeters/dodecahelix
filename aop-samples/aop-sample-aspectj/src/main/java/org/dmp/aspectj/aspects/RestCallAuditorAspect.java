package org.dmp.aspectj.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class RestCallAuditorAspect {
    
    private static final Logger logger = Logger.getLogger(RestCallAuditorAspect.class);

    @Pointcut("execution(* *.*(..))")
    public void allCalls() {}
    
    @Pointcut("execution(* org.dmp.aspectj.rest.*RestService.*RestCall(..))")
    public void restCalls() {}
    
    @Around("restCalls()")
    public Object aroundRestCalls(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        
        logger.info("Before " + thisJoinPoint.getSignature());
        Object returnObj = thisJoinPoint.proceed();
        logger.info("After " + thisJoinPoint.getSignature());
        
        return returnObj;
    }
    
}
