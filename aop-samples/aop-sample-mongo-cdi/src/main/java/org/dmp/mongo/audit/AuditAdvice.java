package org.dmp.mongo.audit;

import java.util.Arrays;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

@Audited @Interceptor
public class AuditAdvice {
    
    @Inject
    private transient Logger logger;
    
    @AroundInvoke
    public Object auditMethod(InvocationContext joinPoint) throws Exception {
        Object returnObj = null;
        String invocationMethod = joinPoint.getMethod().getName();
        try {
            logger.info("Auditor:  Invoking " + invocationMethod 
                    + "; Class=" + joinPoint.getTarget().getClass().getName() 
                    + "; Arguments=" + Arrays.toString(joinPoint.getParameters())
                    + "; Context=" + joinPoint.getContextData());
            
            returnObj = joinPoint.proceed();
            return returnObj;
        } 
        finally {
            logger.info("Auditor:  Return from method " + invocationMethod + "; Returning " + returnObj);
        }
    }

}
