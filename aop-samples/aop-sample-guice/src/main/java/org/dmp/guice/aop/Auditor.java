package org.dmp.guice.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

public class Auditor implements MethodInterceptor {
    
    private static final Logger logger = Logger.getLogger(Auditor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object returnObj = null;
        String invocationMethod = invocation.getMethod().getName();
        try {
            logger.info("Auditor:  Invoking " + invocationMethod + "; Arguments " + invocation.getArguments());
            returnObj = invocation.proceed();
            return returnObj;
        } 
        finally {
            logger.info("Auditor:  Return from method " + invocationMethod + "; Returning " + returnObj);
        }
    }

}
